package com.alacriti.paymentGateway.payment.gateway.api.merchant.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.alacriti.paymentGateway.payment.gateway.api.common.Currency;
import com.alacriti.paymentGateway.payment.gateway.api.common.Status;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.entity.MerchantEntity;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.entity.PaymentEntity;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.model.Payment;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.model.PaymentResponse;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.repository.MerchantRepository;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	MerchantRepository merchantRepository;

	@Autowired
	PaymentRepository paymentRepository;

	/*
	 * @Autowired JdbcTemplate jdbcTemplate;
	 */

	

	
	
	
	
	public PaymentResponse requestPaymentStatus(Payment requestDetails) {

		PaymentEntity entity = new PaymentEntity();
		String message = "SUCESSFUL.....";

		try {
			validatecurrency(requestDetails);
			validateMerchantId(requestDetails.getMerchantId());
			saveRequestData(requestDetails, Status.SUCESS.status, entity);

		} catch (Exception e) {
			saveRequestData(requestDetails, Status.FAIL.status, entity);
			message = e.getMessage();
		}

		PaymentResponse response = sendPaymentStatus(entity, message);

		return response;

	}
	
	
	
	

	public void validatecurrency(Payment payment) throws Exception {

		String error = "";

		if (payment.getCurrency() == null || !payment.getCurrency().equals(Currency.USA.currency)) {

			error = error + "invalid currency ";
		}

		if (payment.getMerchantId() == null || payment.getMerchantId() == "") {

			error = error + "No input, empty merchant id ";
		}

		

		try {
			if (Double.parseDouble(payment.getAmount()) == 0.00) {

				error = error + "no input, enter amount ";
			}
		} catch (Exception f) {
			error = error + "invalid amount ";
		}

		if (error != "") {
			throw new Exception(error);
		}

	}
	
	
	
	

	public PaymentResponse sendPaymentStatus(PaymentEntity entity, String message) {

		PaymentResponse response = new PaymentResponse();

		response.setMerchantId(entity.getMerchantId());
		response.setOrderId(entity.getOrderId());
		response.setTransactionId(entity.gettransactionId());
		response.setStatus(entity.getStatus());
		response.setMessage(message);
		response.setAmount(entity.getAmount());

		return response;

	}
	
	
	
	

	public void saveRequestData(Payment payment, String status, PaymentEntity paymentEntity) {

		paymentEntity.setMerchantId(payment.getMerchantId());
		paymentEntity.setAmount(payment.getAmount());
		paymentEntity.setCurrency(payment.getCurrency());
		paymentEntity.setOrderId("O-ID" + Math.random());
		paymentEntity.setTransaction_id("T-ID" + Math.random());
		paymentEntity.setStatus(status);

		paymentRepository.save(paymentEntity);

	}
	
	
	
	

	public List<PaymentResponse> returnTransactionStatus(String transactionID) {
		/*
		 * PaymentResponse response = new PaymentResponse();
		 * 
		 * List<PaymentResponse> listOfResponse = new ArrayList<>(); String sql =
		 * "select * from payment_info where transaction_id ='" + transactionID + "'";
		 * listOfResponse = jdbcTemplate.query(sql, new PaymentResponseMapper());
		 */
		List<PaymentEntity> pEntity = paymentRepository.findByTransactionId(transactionID);

		List<PaymentResponse> responseList = new ArrayList<>();

		for (PaymentEntity e : pEntity) {
			PaymentResponse response = new PaymentResponse();

			response.setMerchantId(e.getMerchantId());
			response.setTransactionId(e.gettransactionId());
			response.setStatus(e.getStatus());
			response.setOrderId(e.getOrderId());
			response.setAmount(e.getAmount());
			

			responseList.add(response);

		}

		return responseList;
	}

	
	
	
	
	/*
	 * private class PaymentResponseMapper implements RowMapper<PaymentResponse> {
	 * 
	 * @Override public PaymentResponse mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { PaymentResponse response = new PaymentResponse(); String id =
	 * rs.getString("transaction_id"); String status = rs.getString("status");
	 * String mId = rs.getString("merchant_id"); String Oid =
	 * rs.getString("order_id");
	 * 
	 * response.setTransactionId(id); response.setStatus(status);
	 * response.setMerchantId(mId); response.setOrderId(Oid);
	 * 
	 * return response;
	 * 
	 * }
	 * 
	 * }
	 */

	public void validateMerchantId(String mID) throws Exception {

		List<MerchantEntity> merchantIDList = merchantRepository.findByMerchantId(mID);

		if (merchantIDList.isEmpty()) {
			throw new Exception("merchant not registered!!! please register before procee..");
		}

		
	}

}



