package com.alacriti.paymentGateway.payment.gateway.api.merchant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alacriti.paymentGateway.payment.gateway.api.merchant.entity.MerchantEntity;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.model.Merchant;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.model.MerchantResponse;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.model.PaymentResponse;
import com.alacriti.paymentGateway.payment.gateway.api.merchant.repository.MerchantRepository;

@Service
public class MerchantService {

	@Autowired
	private MerchantRepository merchantRepository;

	/**
	 * this method will register the pojo merchant details into entity
	 */

	public MerchantResponse saveMerchantDetails(Merchant merchant) {

		MerchantEntity entity = new MerchantEntity();
		
		
		
		

		String message = "data is saves Sucessfully!!!";

		try {
			validateData(merchant);
			saveMerchantData(merchant,entity);
			

		} catch (Exception e) {
			saveMerchantData(merchant,entity);
			message = e.getMessage();
			
		}
		
		MerchantResponse response = sendMerchantRegisterStatus(entity, message);
		
		return response;
	}

	public void saveMerchantData(Merchant merchant,MerchantEntity entity) {

		
		entity.setMerchantId("M-ID" + Math.random());
		entity.setMerchantName(merchant.getMerchantName());
		entity.setMerchantEmail(merchant.getMerchantEmail());
		entity.setMerchantBusinessType(merchant.getMerchantBusinesstype());
		entity.setMerchantAddress(merchant.getMerchantAddress());
		entity.setMerchantPhone(merchant.getMerchantPhone());

		merchantRepository.save(entity);

	}

	public void validateData(Merchant merchant) throws Exception {

		String merchantMessage = "created sucessfully!!!";

		if (merchant.getMerchantName() == null || merchant.getMerchantName() == "") {
			throw new Exception("no field/invalid merchant id /");
		}
		if (merchant.getMerchantEmail() == null || merchant.getMerchantEmail() == "") {
			throw new Exception("no field/invalid merchant email /");
		}
		if (merchant.getMerchantBusinesstype() == null || merchant.getMerchantBusinesstype() == "") {
			throw new Exception("no field/invalid merchant Business type /");
		}
		if (merchant.getMerchantAddress() == null || merchant.getMerchantAddress() == "") {
			throw new Exception("no field/invalid merchant address /");
		}
		if (merchant.getMerchantPhone() == 0) {
			throw new Exception("no field/invalid merchant phone /");

		}

		if (merchantMessage != "") {
			throw new Exception(merchantMessage);
		}

	}
	
	public MerchantResponse sendMerchantRegisterStatus(MerchantEntity entity,String message) {
		
		MerchantResponse response = new MerchantResponse();
		response.setMerchantName(entity.getMerchantName());
		response.setMerchantEmail(entity.getMerchantEmail());
		response.setMerchantAddress(entity.getMerchantAddress());
		response.setMerchantBusinesstype(entity.getMerchantBusinessType());
		response.setMerchantPhone(entity.getMerchantPhone());
		response.setMessage(message);
		
		return response;
	}

}
