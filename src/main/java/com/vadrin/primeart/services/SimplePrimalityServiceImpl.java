package com.vadrin.primeart.services;

import java.math.BigInteger;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class SimplePrimalityServiceImpl implements PrimalityService {

	private boolean isPrime(String input) {
        BigInteger b = new BigInteger(input); 
        return b.isProbablePrime(1); 
	}

	@Override
	public String nextPrime(String input) {
		BigInteger b = new BigInteger(input);
		return b.nextProbablePrime().toString();
	}

}
