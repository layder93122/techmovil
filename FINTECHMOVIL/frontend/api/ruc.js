export default async function handler(req, res) {
  const { numero } = req.query;
  if (!numero || !/^\d{11}$/.test(numero)) {
    return res.status(400).json({ error: "RUC debe tener 11 dígitos" });
  }
  const token = process.env.APIS_TOKEN || process.env.VITE_APIS_TOKEN;
  try {
    const r = await fetch(
      `https://api.apis.net.pe/v2/sunat/ruc?numero=${numero}`,
      { headers: { Authorization: `Bearer ${token}`, Accept: "application/json" } }
    );
    const data = await r.json();
    res.status(r.status).json(data);
  } catch (e) {
    res.status(500).json({ error: "Error de conexión con SUNAT" });
  }
}
