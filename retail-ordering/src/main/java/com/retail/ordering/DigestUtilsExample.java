package com.retail.ordering;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class DigestUtilsExample {
	public static void main(String[] args) {
		System.out.println(DigestUtils.md5Hex("!ad#@123sdfskmho&(jhgjh)" + "chaitanya"));
		System.out.println(DigestUtils.md5Hex("!ad#@123sdfskmho&(jhgjh)" + "chaitanya"));
		System.out.println(UUID.randomUUID().toString());
	}
}