import { useState, useEffect } from "react";
import {
  Box,
  Button,
  TextField,
  Typography,
  Container,
  Alert,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { productService } from "../services/productService";
import { CurrencyInput } from "../components/CurrencyInput";

export default function ProductPage() {
  const [products, setProducts] = useState([]);
  const [formData, setFormData] = useState({
    name: "",
    description: "",
    price: "",
    quantity: "",
  });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  useEffect(() => {
    loadProducts();
  }, []);

  const loadProducts = async () => {
    try {
      const data = await productService.getProducts();
      setProducts(data);
    } catch (err) {
      setError("Erro ao carregar produtos");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    try {
      const productData = {
        ...formData,
        price: Number(formData.price),
        quantity: Number(formData.quantity),
      };

      await productService.createProduct(productData);
      setSuccess("Produto cadastrado com sucesso!");
      setFormData({
        name: "",
        description: "",
        price: "",
        quantity: "",
      });
      loadProducts();
    } catch (err) {
      setError(err.response?.data?.message || "Erro ao cadastrar produto");
    }
  };

  return (
    <Container maxWidth="lg">
      <Box
        sx={{
          minHeight: "100vh",
          py: 4,
          display: "flex",
          flexDirection: "column",
          gap: 4,
        }}
      >
        <Typography
          variant="h4"
          sx={{
            background: "linear-gradient(45deg, #7C3AED 30%, #06B6D4 90%)",
            backgroundClip: "text",
            WebkitBackgroundClip: "text",
            WebkitTextFillColor: "transparent",
          }}
        >
          Cadastro de Produtos
        </Typography>

        <Paper
          sx={{
            p: 4,
            backdropFilter: "blur(8px)",
            backgroundColor: "rgba(31, 41, 55, 0.8)",
          }}
        >
          <Box component="form" onSubmit={handleSubmit}>
            {error && (
              <Alert severity="error" sx={{ mb: 3 }}>
                {error}
              </Alert>
            )}
            {success && (
              <Alert severity="success" sx={{ mb: 3 }}>
                {success}
              </Alert>
            )}

            <Box
              sx={{
                display: "grid",
                gap: 3,
                gridTemplateColumns: {
                  xs: "1fr",
                  md: "repeat(2, 1fr)",
                },
              }}
            >
              <TextField
                required
                fullWidth
                label="Nome"
                name="name"
                value={formData.name}
                onChange={handleChange}
              />
              <TextField
                required
                fullWidth
                label="Descrição"
                name="description"
                value={formData.description}
                onChange={handleChange}
              />
              <CurrencyInput
                required
                fullWidth
                label="Preço"
                name="price"
                value={formData.price}
                onChange={handleChange}
              />
              <TextField
                required
                fullWidth
                label="Quantidade"
                name="quantity"
                type="number"
                value={formData.quantity}
                onChange={handleChange}
                inputProps={{ min: 0 }}
              />
            </Box>

            <Button
              type="submit"
              variant="contained"
              sx={{
                mt: 4,
                height: 48,
                background: "linear-gradient(45deg, #7C3AED 30%, #06B6D4 90%)",
                "&:hover": {
                  background:
                    "linear-gradient(45deg, #6D28D9 30%, #0891B2 90%)",
                },
              }}
            >
              Cadastrar Produto
            </Button>
          </Box>
        </Paper>

        <Typography
          variant="h5"
          sx={{
            mt: 6,
            mb: 3,
            background: "linear-gradient(45deg, #7C3AED 30%, #06B6D4 90%)",
            backgroundClip: "text",
            WebkitBackgroundClip: "text",
            WebkitTextFillColor: "transparent",
          }}
        >
          Produtos Cadastrados
        </Typography>

        <TableContainer
          component={Paper}
          sx={{
            backdropFilter: "blur(8px)",
            backgroundColor: "rgba(31, 41, 55, 0.8)",
          }}
        >
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Nome</TableCell>
                <TableCell>Descrição</TableCell>
                <TableCell align="right">Preço</TableCell>
                <TableCell align="right">Quantidade</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {products.map((product) => (
                <TableRow key={product.id}>
                  <TableCell>{product.name}</TableCell>
                  <TableCell>{product.description}</TableCell>
                  <TableCell align="right">
                    {product.price.toLocaleString("pt-BR", {
                      style: "currency",
                      currency: "BRL",
                    })}
                  </TableCell>
                  <TableCell align="right">{product.quantity}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Box>
    </Container>
  );
}
