package provided.entity;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class EntityBase {

	protected String toFraction(BigDecimal value) {
		String result = "";
		Double amount = Objects.isNull(value) ? null : value.doubleValue();

		if (Objects.nonNull(amount) && amount > 0.0) {
			int wholePart = Double.valueOf(Math.floor(amount)).intValue();
			double fractionalPart = amount - wholePart;
			Factor twoFactor = findFactor(fractionalPart, 16, 2);
			Factor threeFactor = findFactor(fractionalPart, 15, 5);

			Factor factor = twoFactor.factor < threeFactor.factor ? twoFactor : threeFactor;

			if (wholePart > 0) {
				result += Integer.valueOf(wholePart).toString();
			}

			if (factor.num != 0) {

				if (!result.isEmpty()) {
					result += " ";
				}

				result += factor;
			}

			result += " ";
		}

		return result;
	}

	private Factor findFactor(double fractionalPart, int factor, int divisor) {

		int num = Double.valueOf(Math.round(fractionalPart * factor)).intValue();

		while (num != 0 && num % divisor == 0 && factor % divisor == 0) {
			num /= divisor;
			factor /= divisor;
		}

		return new Factor(num, factor);
	}

	private static class Factor {
		int num;
		int factor;

		Factor(int num, int factor) {
			this.num = num;
			this.factor = factor;
		}

		@Override
		public String toString() {
			return num + "/" + factor;
		}
	}

}
