/*
 * Copyright 2018 The Code Department
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tcdng.unify.core.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.tcdng.unify.core.AbstractUnifyComponentTest;
import com.tcdng.unify.core.ApplicationComponents;
import com.tcdng.unify.core.TestTaskMonitor;
import com.tcdng.unify.core.business.BusinessLogicInput;
import com.tcdng.unify.core.business.BusinessLogicOutput;
import com.tcdng.unify.core.business.BusinessLogicUnit;
import com.tcdng.unify.core.database.Database;
import com.tcdng.unify.core.database.DatabaseTransactionManager;
import com.tcdng.unify.core.database.Query;
import com.tcdng.unify.core.util.IOUtils;

/**
 * Batch processing unit tests. Note that for these tests batches are
 * categorized by currency.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class DBBatchFileReadProcessorTest extends AbstractUnifyComponentTest {

	private Database db;

	private DatabaseTransactionManager tm;

	@SuppressWarnings("unchecked")
	@Test
	public void testSingleBatchBatchProcessing() throws Exception {
		// Setup parameters
		BusinessLogicInput input = new BusinessLogicInput(new TestTaskMonitor(), db.getName());
		BatchFileConfig fileBulkConfig = BatchFileReaderTestUtils.createSampleFixedLengthBatchConfig(true);
		byte[][] fileObject = new byte[1][];
		fileObject[0] = IOUtils.createInMemoryTextFile("0123456789Abel Turner         NGN0000000020000",
				"6758495839Bamanga Tukur       NGN0000000052000");
		input.setParameter(BatchFileReadProcessorInputConstants.BATCHFILECONFIG, fileBulkConfig);
		input.setParameter(BatchFileReadProcessorInputConstants.FILEOBJECTS, fileObject);

		// Perform batch file processing and do some assertions
		BusinessLogicOutput output = new BusinessLogicOutput();
		BusinessLogicUnit blu = (BusinessLogicUnit) getComponent("test-batchfileprocessor-b");
		tm.beginTransaction();
		try {
			blu.execute(input, output);
		} finally {
			tm.endTransaction();
		}

		Map<String, TestBatchRecordB> result = output.getResult(Map.class,
				BatchFileReadProcessorOutputConstants.BATCHFILEREADRESULT);
		assertNotNull(result);
		assertEquals(1, result.size());
		TestBatchRecordB batchRecord = result.get("NGN");
		assertNotNull(batchRecord);
		assertEquals("NGN", batchRecord.getCategory());
		assertEquals("NGN", batchRecord.getCurrency());
		assertEquals(Integer.valueOf(2), batchRecord.getItemCount());
		assertEquals(Double.valueOf(20000 + 52000), batchRecord.getTotalAmount());

		// Assert persistent records are created
		TestBatchRecordB qryBatchRecord = null;
		tm.beginTransaction();
		try {
			qryBatchRecord = db.find(new Query<TestBatchRecordB>(TestBatchRecordB.class).equals("currency", "NGN"));
		} finally {
			tm.endTransaction();
		}

		assertNotNull(qryBatchRecord);
		assertEquals("NGN", qryBatchRecord.getCategory());
		assertEquals("NGN", qryBatchRecord.getCurrency());
		assertEquals(Integer.valueOf(2), qryBatchRecord.getItemCount());
		assertEquals(Double.valueOf(20000 + 52000), qryBatchRecord.getTotalAmount());

		Long batchId = qryBatchRecord.getId();
		List<TestBatchItemRecordB> batchItemList = null;
		tm.beginTransaction();
		try {
			batchItemList = db.findAll(
					new Query<TestBatchItemRecordB>(TestBatchItemRecordB.class).equals("batchId", batchId).order("id"));
		} finally {
			tm.endTransaction();
		}

		assertNotNull(batchItemList);
		assertEquals(2, batchItemList.size());

		TestBatchItemRecordB batchItemRecord = batchItemList.get(0);
		assertEquals(batchId, batchItemRecord.getBatchId());
		assertEquals("Abel Turner", batchItemRecord.getBeneficiary());
		assertEquals("0123456789", batchItemRecord.getAccountNo());
		assertEquals("NGN", batchItemRecord.getCurrency());
		assertEquals(Double.valueOf(20000), batchItemRecord.getAmount());

		batchItemRecord = batchItemList.get(1);
		assertEquals(batchId, batchItemRecord.getBatchId());
		assertEquals("Bamanga Tukur", batchItemRecord.getBeneficiary());
		assertEquals("6758495839", batchItemRecord.getAccountNo());
		assertEquals("NGN", batchItemRecord.getCurrency());
		assertEquals(Double.valueOf(52000), batchItemRecord.getAmount());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMultipleBatchBatchProcessing() throws Exception {
		// Setup parameters
		BusinessLogicInput input = new BusinessLogicInput(new TestTaskMonitor(), db.getName());
		BatchFileConfig fileBulkConfig = BatchFileReaderTestUtils.createSampleFixedLengthBatchConfig(true);
		byte[][] fileObject = new byte[1][];
		fileObject[0] = IOUtils.createInMemoryTextFile("0123456789Abel Turner         NGN0000000020000",
				"2300000001Big Bird            USD0000000000400", "6758495839Bamanga Tukur       NGN0000000052000");
		input.setParameter(BatchFileReadProcessorInputConstants.BATCHFILECONFIG, fileBulkConfig);
		input.setParameter(BatchFileReadProcessorInputConstants.FILEOBJECTS, fileObject);

		// Perform batch file processing and do some assertions
		BusinessLogicOutput output = new BusinessLogicOutput();
		BusinessLogicUnit blu = (BusinessLogicUnit) getComponent("test-batchfileprocessor-b");
		tm.beginTransaction();
		try {
			blu.execute(input, output);
		} finally {
			tm.endTransaction();
		}

		Map<String, TestBatchRecordB> result = output.getResult(Map.class,
				BatchFileReadProcessorOutputConstants.BATCHFILEREADRESULT);
		assertEquals(2, result.size());
		TestBatchRecordB batchRecord = result.get("NGN");
		assertNotNull(batchRecord);
		assertEquals("NGN", batchRecord.getCategory());
		assertEquals("NGN", batchRecord.getCurrency());
		assertEquals(Integer.valueOf(2), batchRecord.getItemCount());
		assertEquals(Double.valueOf(20000 + 52000), batchRecord.getTotalAmount());

		batchRecord = result.get("USD");
		assertNotNull(batchRecord);
		assertEquals("USD", batchRecord.getCategory());
		assertEquals("USD", batchRecord.getCurrency());
		assertEquals(Integer.valueOf(1), batchRecord.getItemCount());
		assertEquals(Double.valueOf(400), batchRecord.getTotalAmount());

		// Assert persistent record are created
		TestBatchRecordB ngnBatchRecord = null;
		TestBatchRecordB usdBatchRecord = null;
		tm.beginTransaction();
		try {
			ngnBatchRecord = db.find(new Query<TestBatchRecordB>(TestBatchRecordB.class).equals("currency", "NGN"));
			usdBatchRecord = db.find(new Query<TestBatchRecordB>(TestBatchRecordB.class).equals("currency", "USD"));
		} finally {
			tm.endTransaction();
		}

		assertNotNull(ngnBatchRecord);
		assertEquals("NGN", ngnBatchRecord.getCategory());
		assertEquals("NGN", ngnBatchRecord.getCurrency());
		assertEquals(Integer.valueOf(2), ngnBatchRecord.getItemCount());
		assertEquals(Double.valueOf(20000 + 52000), ngnBatchRecord.getTotalAmount());

		assertNotNull(usdBatchRecord);
		assertEquals("USD", usdBatchRecord.getCategory());
		assertEquals("USD", usdBatchRecord.getCurrency());
		assertEquals(Integer.valueOf(1), usdBatchRecord.getItemCount());
		assertEquals(Double.valueOf(400), usdBatchRecord.getTotalAmount());

		Long ngnBatchId = ngnBatchRecord.getId();
		Long usdBatchId = usdBatchRecord.getId();
		List<TestBatchItemRecordB> ngnBatchItemList = null;
		List<TestBatchItemRecordB> usdBatchItemList = null;
		tm.beginTransaction();
		try {
			ngnBatchItemList = db.findAll(new Query<TestBatchItemRecordB>(TestBatchItemRecordB.class)
					.equals("batchId", ngnBatchId).order("id"));
			usdBatchItemList = db.findAll(new Query<TestBatchItemRecordB>(TestBatchItemRecordB.class)
					.equals("batchId", usdBatchId).order("id"));
		} finally {
			tm.endTransaction();
		}

		assertNotNull(ngnBatchItemList);
		assertEquals(2, ngnBatchItemList.size());
		assertEquals(1, usdBatchItemList.size());

		TestBatchItemRecordB batchItemRecord = ngnBatchItemList.get(0);
		assertEquals(ngnBatchId, batchItemRecord.getBatchId());
		assertEquals("Abel Turner", batchItemRecord.getBeneficiary());
		assertEquals("0123456789", batchItemRecord.getAccountNo());
		assertEquals("NGN", batchItemRecord.getCurrency());
		assertEquals(Double.valueOf(20000), batchItemRecord.getAmount());

		batchItemRecord = ngnBatchItemList.get(1);
		assertEquals(ngnBatchId, batchItemRecord.getBatchId());
		assertEquals("Bamanga Tukur", batchItemRecord.getBeneficiary());
		assertEquals("6758495839", batchItemRecord.getAccountNo());
		assertEquals("NGN", batchItemRecord.getCurrency());
		assertEquals(Double.valueOf(52000), batchItemRecord.getAmount());

		batchItemRecord = usdBatchItemList.get(0);
		assertEquals(usdBatchId, batchItemRecord.getBatchId());
		assertEquals("Big Bird", batchItemRecord.getBeneficiary());
		assertEquals("2300000001", batchItemRecord.getAccountNo());
		assertEquals("USD", batchItemRecord.getCurrency());
		assertEquals(Double.valueOf(400), batchItemRecord.getAmount());
	}

	@Override
	protected void onSetup() throws Exception {
		db = (Database) getComponent(ApplicationComponents.APPLICATION_DATABASE);
		tm = (DatabaseTransactionManager) getComponent(ApplicationComponents.APPLICATION_DATABASE);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onTearDown() throws Exception {
		deleteAll(TestBatchItemRecordA.class, TestBatchItemRecordB.class, TestBatchRecordB.class);
	}
}
