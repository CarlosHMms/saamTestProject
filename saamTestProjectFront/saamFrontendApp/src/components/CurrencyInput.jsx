import { TextField } from "@mui/material";
import { NumericFormat } from "react-number-format";

export const CurrencyInput = ({ value, onChange, ...props }) => {
  const handleChange = (values) => {
    onChange({
      target: {
        name: props.name,
        value: values.floatValue || 0,
      },
    });
  };

  return (
    <NumericFormat
      {...props}
      value={value}
      customInput={TextField}
      thousandSeparator="."
      decimalSeparator=","
      prefix="R$ "
      decimalScale={2}
      fixedDecimalScale
      allowNegative={false}
      onValueChange={handleChange}
    />
  );
};
