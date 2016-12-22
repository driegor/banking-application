package com.company.ebanking.common.dto.account;

public class BalanceDTO {

	int ammount;

	public int getAmmount() {
		return ammount;
	}

	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}

	public static class BalanceDTOBuilder {

		private BalanceDTO instance;

		private BalanceDTOBuilder() {
			instance = new BalanceDTO();
		}

		public static BalanceDTOBuilder builder() {
			return new BalanceDTOBuilder();
		}

		public BalanceDTOBuilder ammount(int ammount) {
			instance.setAmmount(ammount);
			return this;
		}

		public BalanceDTO build() {
			return instance;
		}
	}

}
