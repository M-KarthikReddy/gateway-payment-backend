package com.alacriti.paymentGateway.payment.gateway.api.merchant.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alacriti.paymentGateway.payment.gateway.api.merchant.model.Merchant;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.model.MerchantResponse;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.model.Payment;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.model.PaymentResponse;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.service.MerchantService;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.service.PaymentService;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class MerchantController {
	@Autowired
	MerchantService merchantService;
	
	@Autowired
	PaymentService paymentService;
	
	@CrossOrigin(origins = "*")
	@PostMapping("/register_merchant")
	public MerchantResponse setMerchantData(@RequestBody Merchant merchant) {
		
		MerchantResponse response = merchantService.saveMerchantDetails(merchant);
		return response;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/payment")
	public PaymentResponse requestPayment(@RequestBody Payment payment) {
		PaymentResponse response = paymentService.requestPaymentStatus(payment);
		return response;
		
		}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/payment_status/{transactionID}")
	public List<PaymentResponse> getStatus(@PathVariable String transactionID){
		
		return paymentService.returnTransactionStatus(transactionID);
		
		
//		return new ArrayList<PaymentResponse>();
		
		
	}
	
	
}
