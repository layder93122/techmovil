export default async function handler(req, res) {
  const { numero } = req.query;
  if (!numero) return res.status(400).json({ error: "Falta el número de DNI" });

  const r = await fetch(`https://api.apis.net.pe/v1/dni?numero=${numero}`, {
    headers: { Authorization: "Bearer sk_16558.F5SQcVQRLtkdhNZ6A4v6UdnNt7bI2EQz" },
  });

  const text = await r.text();
  res.status(r.status).setHeader("Content-Type", "application/json").send(text);
}
