export default async function handler(req, res) {
  const { numero } = req.query;
  if (!numero || !/^\d{8}$/.test(numero)) {
    return res.status(400).json({ error: "DNI debe tener 8 dígitos" });
  }
  const token = "sk_16558.36uckoUCQU8XLg2FTN4UnXTogrfsc4TI";
  try {
    const r = await fetch(
      `https://api.apis.net.pe/v1/dni?numero=${numero}`,
      { headers: { Authorization: `Bearer ${token}`, Accept: "application/json" } }
    );
    const data = await r.json();
    res.status(r.status).json(data);
  } catch (e) {
    res.status(500).json({ error: "Error de conexión con RENIEC" });
  }
}
