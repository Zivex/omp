package com.capinfo.assistant.platform.ws.card.service;

import java.rmi.RemoteException;

import com.capinfo.assistant.platform.ws.card.model.CardPersonMessageBack;

public class Test {
	public static void main(String[] args) {
		CardPersonMessageWsServiceProxy proxy=new CardPersonMessageWsServiceProxy();
		CardPersonMessageBack back=null; 
		try {
			back=proxy.getCardPersonMessageByIDNumber("410305194906192524");
			System.out.println(back.getName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
