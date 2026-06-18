/* ════════════════════════════════════════════════════════════════
   TECHMOVIL — Sistema Completo de Gestión (React 19)
   Módulos: Dashboard · Productos · Inventario · Ventas · Reportes · Clientes
   Funciona 100% con datos mock + conecta al backend si está disponible
════════════════════════════════════════════════════════════════ */
import { useState, useEffect, useCallback, useRef } from "react";

const API = import.meta.env.VITE_API_URL || "http://localhost:8080/api";
const APIS_TOKEN = import.meta.env.VITE_APIS_TOKEN || "";
const authHeader = () => {
  const t = localStorage.getItem("techmovil_token");
  return t ? { "Authorization": `Bearer ${t}`, "Content-Type": "application/json" } : { "Content-Type": "application/json" };
};
const apisHeader = () => ({ "Authorization": `Bearer ${APIS_TOKEN}` });

const parseApiRes = async (res) => {
  if (res.status === 429) throw new Error("Límite de consultas alcanzado, espera un momento");
  if (res.status === 401) throw new Error("Token inválido");
  if (res.status === 404) throw new Error("No encontrado");
  const text = await res.text();
  try { return JSON.parse(text); } catch { throw new Error("Error de conexión con la API"); }
};

const consultarDNI = async (dni) => {
  const res = await fetch(`/api/dni?numero=${dni}`);
  const d = await parseApiRes(res);
  if (d.message || d.error) throw new Error(d.message || d.error);
  return d.nombre || `${d.nombres} ${d.apellidoPaterno} ${d.apellidoMaterno}`.trim();
};

const consultarRUC = async (ruc) => {
  const res = await fetch(`/api/ruc?numero=${ruc}`);
  const d = await parseApiRes(res);
  if (d.message || d.error) throw new Error(d.message || d.error);
  return d;
};

/* ── Estilos ──────────────────────────────────────────────────── */
const CSS = `
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&family=JetBrains+Mono:wght@400;500&display=swap');
*,*::before,*::after{box-sizing:border-box;margin:0;padding:0}
:root{
  --bg:#F0F4F8;--sidebar:#1E293B;--sidebar2:#0F172A;--card:#FFFFFF;
  --blue:#2563EB;--blue2:#1D4ED8;--blue3:#DBEAFE;--blue4:#EFF6FF;
  --green:#10B981;--green2:#ECFDF5;--green3:#059669;
  --amber:#F59E0B;--amber2:#FFFBEB;--amber3:#D97706;
  --red:#EF4444;--red2:#FEF2F2;--red3:#DC2626;
  --purple:#8B5CF6;--purple2:#F5F3FF;
  --text:#1E293B;--text2:#475569;--text3:#94A3B8;
  --border:#E2E8F0;--border2:#CBD5E1;
  --font:'Inter',sans-serif;--mono:'JetBrains Mono',monospace;
  --r:12px;--r2:8px;--shadow:0 1px 3px rgba(0,0,0,.1),0 1px 2px rgba(0,0,0,.06);
  --shadow2:0 4px 6px rgba(0,0,0,.07),0 2px 4px rgba(0,0,0,.06);
  --shadow3:0 10px 25px rgba(0,0,0,.1);
}
body{background:var(--bg);color:var(--text);font-family:var(--font);min-height:100vh;overflow-x:hidden}
input,select,textarea,button{font-family:var(--font)}
*::-webkit-scrollbar{width:4px;height:4px}
*::-webkit-scrollbar-track{background:transparent}
*::-webkit-scrollbar-thumb{background:var(--border2);border-radius:4px}
@keyframes fadeUp{from{opacity:0;transform:translateY(12px)}to{opacity:1;transform:translateY(0)}}
@keyframes fadeIn{from{opacity:0}to{opacity:1}}
@keyframes spin{to{transform:rotate(360deg)}}
@keyframes slideIn{from{transform:translateX(-100%)}to{transform:translateX(0)}}
@keyframes pulse{0%,100%{opacity:1}50%{opacity:.5}}

/* APP LAYOUT */
.app-shell{display:flex;height:100vh;overflow:hidden}

/* SIDEBAR */
.sidebar{
  width:256px;flex-shrink:0;background:var(--sidebar2);
  display:flex;flex-direction:column;overflow-y:auto;
  transition:width .25s ease;position:relative;z-index:20;
}
.sidebar.collapsed{width:64px}
.sidebar-logo{
  display:flex;align-items:center;gap:12px;
  padding:20px 16px;border-bottom:1px solid rgba(255,255,255,.06);
}
.logo-icon{width:36px;height:36px;background:var(--blue);border-radius:10px;display:flex;align-items:center;justify-content:center;font-size:18px;flex-shrink:0}
.logo-text{font-size:16px;font-weight:800;color:#fff;letter-spacing:-.3px;white-space:nowrap;overflow:hidden}
.logo-sub{font-size:9px;color:#64748B;font-family:var(--mono);letter-spacing:.5px;text-transform:uppercase}
.sidebar-section{padding:8px 8px 4px;font-size:10px;font-weight:700;color:#475569;letter-spacing:1px;text-transform:uppercase;margin-top:8px;white-space:nowrap;overflow:hidden}
.nav-item{
  display:flex;align-items:center;gap:12px;
  padding:10px 12px;border-radius:10px;margin:1px 8px;
  cursor:pointer;transition:all .15s;position:relative;
  color:#94A3B8;font-size:13px;font-weight:500;
}
.nav-item:hover{background:rgba(255,255,255,.06);color:#CBD5E1}
.nav-item.active{background:var(--blue);color:#fff;box-shadow:0 4px 12px rgba(37,99,235,.4)}
.nav-icon{font-size:17px;flex-shrink:0;width:20px;text-align:center}
.nav-label{white-space:nowrap;overflow:hidden}
.nav-badge{margin-left:auto;background:var(--red);color:#fff;font-size:10px;font-weight:700;width:18px;height:18px;border-radius:50%;display:flex;align-items:center;justify-content:center;flex-shrink:0}
.sidebar-footer{margin-top:auto;padding:12px 8px;border-top:1px solid rgba(255,255,255,.06)}
.user-card{display:flex;align-items:center;gap:10px;padding:8px;border-radius:8px;cursor:pointer;transition:all .15s}
.user-card:hover{background:rgba(255,255,255,.06)}
.user-avatar{width:32px;height:32px;border-radius:50%;background:var(--blue);display:flex;align-items:center;justify-content:center;font-size:14px;flex-shrink:0}
.user-name{font-size:13px;font-weight:600;color:#CBD5E1;white-space:nowrap}
.user-role{font-size:10px;color:#64748B;font-family:var(--mono)}

/* MAIN */
.main-area{flex:1;overflow:auto;display:flex;flex-direction:column}

/* TOPBAR */
.topbar{
  background:var(--card);border-bottom:1px solid var(--border);
  padding:0 24px;height:60px;display:flex;align-items:center;gap:12px;
  position:sticky;top:0;z-index:10;box-shadow:var(--shadow);
}
.topbar-title{font-size:18px;font-weight:700;flex:1}
.topbar-search{
  display:flex;align-items:center;gap:8px;
  background:var(--bg);border:1px solid var(--border2);
  border-radius:20px;padding:7px 14px;
  font-size:13px;color:var(--text3);width:240px;
  transition:all .2s;cursor:pointer;
}
.topbar-search:hover{border-color:var(--blue);color:var(--text)}
.topbar-icon-btn{width:36px;height:36px;border-radius:8px;border:1px solid var(--border);background:var(--card);display:flex;align-items:center;justify-content:center;cursor:pointer;font-size:16px;position:relative;transition:all .15s}
.topbar-icon-btn:hover{border-color:var(--blue);background:var(--blue4)}
.notif-dot{position:absolute;top:6px;right:6px;width:7px;height:7px;border-radius:50%;background:var(--red);border:1.5px solid white}

/* PAGE */
.page-content{padding:24px;flex:1}
.page-header{display:flex;align-items:center;justify-content:space-between;margin-bottom:24px;flex-wrap:wrap;gap:12px}
.page-title{font-size:22px;font-weight:700;color:var(--text)}
.page-sub{font-size:13px;color:var(--text3);margin-top:2px}
.breadcrumb{font-size:12px;color:var(--text3);font-family:var(--mono);margin-bottom:4px}

/* CARDS */
.card{background:var(--card);border-radius:var(--r);border:1px solid var(--border);box-shadow:var(--shadow);overflow:hidden}
.card-header{display:flex;align-items:center;justify-content:space-between;padding:16px 20px;border-bottom:1px solid var(--border)}
.card-title{font-size:14px;font-weight:600;color:var(--text)}
.card-body{padding:20px}

/* KPI CARDS */
.kpi-grid{display:grid;grid-template-columns:repeat(4,1fr);gap:16px;margin-bottom:24px}
.kpi-card{
  background:var(--card);border-radius:var(--r);border:1px solid var(--border);
  padding:20px;box-shadow:var(--shadow);display:flex;align-items:flex-start;gap:14px;
  position:relative;overflow:hidden;transition:all .2s;
}
.kpi-card:hover{box-shadow:var(--shadow3);transform:translateY(-1px)}
.kpi-card::after{content:'';position:absolute;top:0;right:0;width:60px;height:60px;border-radius:50%;filter:blur(20px);opacity:.15;transform:translate(20px,-20px)}
.kpi-blue::after{background:var(--blue)}
.kpi-green::after{background:var(--green)}
.kpi-amber::after{background:var(--amber)}
.kpi-red::after{background:var(--red)}
.kpi-purple::after{background:var(--purple)}
.kpi-icon-wrap{width:44px;height:44px;border-radius:10px;display:flex;align-items:center;justify-content:center;font-size:20px;flex-shrink:0}
.kpi-blue .kpi-icon-wrap{background:var(--blue3);color:var(--blue)}
.kpi-green .kpi-icon-wrap{background:var(--green2);color:var(--green3)}
.kpi-amber .kpi-icon-wrap{background:var(--amber2);color:var(--amber3)}
.kpi-red .kpi-icon-wrap{background:var(--red2);color:var(--red3)}
.kpi-purple .kpi-icon-wrap{background:var(--purple2);color:var(--purple)}
.kpi-label{font-size:11px;font-weight:600;color:var(--text3);text-transform:uppercase;letter-spacing:.5px;margin-bottom:6px;font-family:var(--mono)}
.kpi-val{font-size:26px;font-weight:800;color:var(--text);line-height:1;margin-bottom:6px}
.kpi-change{font-size:12px;font-weight:500}
.kpi-up{color:var(--green3)}
.kpi-down{color:var(--red3)}

/* CHARTS */
.charts-row{display:grid;grid-template-columns:2fr 1fr;gap:16px;margin-bottom:24px}
.chart-area{height:200px;position:relative}
.chart-bars{display:flex;align-items:flex-end;gap:6px;height:180px;padding:0 0 24px}
.chart-bar-wrap{flex:1;display:flex;flex-direction:column;align-items:center;gap:4px;height:100%}
.chart-bar-inner{width:100%;display:flex;flex-direction:column;justify-content:flex-end;flex:1}
.chart-bar{width:100%;border-radius:6px 6px 0 0;transition:all .5s ease;cursor:pointer;position:relative}
.chart-bar:hover{filter:brightness(1.1)}
.chart-bar-lbl{font-size:10px;color:var(--text3);font-family:var(--mono)}
.chart-line-svg{width:100%;height:100%}
.doughnut-wrap{display:flex;flex-direction:column;align-items:center;justify-content:center;height:200px;gap:16px}
.legend-item{display:flex;align-items:center;gap:8px;font-size:12px;color:var(--text2)}
.legend-dot{width:10px;height:10px;border-radius:50%;flex-shrink:0}

/* TABLES */
.table-wrap{overflow-x:auto;border-radius:var(--r)}
table{width:100%;border-collapse:collapse;font-size:13px;white-space:nowrap}
thead th{background:var(--bg);color:var(--text3);font-weight:600;font-family:var(--mono);font-size:11px;text-transform:uppercase;letter-spacing:.5px;padding:10px 14px;text-align:left;border-bottom:1px solid var(--border)}
tbody td{padding:11px 14px;border-bottom:1px solid var(--border);color:var(--text2);vertical-align:middle}
tbody td:first-child{color:var(--text);font-weight:500}
tbody tr:last-child td{border-bottom:none}
tbody tr:hover td{background:var(--bg)}

/* BADGES */
.badge{display:inline-flex;align-items:center;gap:4px;padding:3px 10px;border-radius:20px;font-size:11px;font-weight:600;white-space:nowrap}
.badge-green{background:var(--green2);color:var(--green3);border:1px solid #A7F3D0}
.badge-red{background:var(--red2);color:var(--red3);border:1px solid #FECACA}
.badge-amber{background:var(--amber2);color:var(--amber3);border:1px solid #FDE68A}
.badge-blue{background:var(--blue4);color:var(--blue);border:1px solid #BFDBFE}
.badge-gray{background:var(--bg);color:var(--text3);border:1px solid var(--border2)}
.badge-purple{background:var(--purple2);color:var(--purple);border:1px solid #DDD6FE}

/* BUTTONS */
.btn{display:inline-flex;align-items:center;gap:6px;padding:9px 18px;border-radius:8px;font-size:13px;font-weight:600;cursor:pointer;transition:all .15s;border:none;letter-spacing:.1px;white-space:nowrap}
.btn-primary{background:var(--blue);color:#fff}
.btn-primary:hover{background:var(--blue2);box-shadow:0 4px 12px rgba(37,99,235,.3)}
.btn-secondary{background:var(--card);color:var(--text);border:1px solid var(--border2)}
.btn-secondary:hover{border-color:var(--blue);color:var(--blue);background:var(--blue4)}
.btn-success{background:var(--green);color:#fff}
.btn-success:hover{background:var(--green3)}
.btn-danger{background:var(--red2);color:var(--red3);border:1px solid #FECACA}
.btn-danger:hover{background:var(--red);color:#fff}
.btn-sm{padding:6px 12px;font-size:12px}
.btn-icon{width:32px;height:32px;padding:0;border-radius:8px;display:flex;align-items:center;justify-content:center;background:var(--bg);border:1px solid var(--border);color:var(--text2);cursor:pointer;font-size:14px;transition:all .15s}
.btn-icon:hover{border-color:var(--blue);color:var(--blue);background:var(--blue4)}
.btn:disabled{opacity:.5;cursor:not-allowed}

/* FORMS */
.form-grid{display:grid;grid-template-columns:1fr 1fr;gap:16px}
.form-grid-3{display:grid;grid-template-columns:1fr 1fr 1fr;gap:16px}
.form-group{display:flex;flex-direction:column;gap:6px}
.form-group.full{grid-column:1/-1}
.form-label{font-size:12px;font-weight:600;color:var(--text2);text-transform:uppercase;letter-spacing:.3px;font-family:var(--mono)}
.form-input{
  background:var(--card);border:1px solid var(--border2);color:var(--text);
  border-radius:8px;padding:9px 12px;font-size:13px;outline:none;
  transition:border-color .2s;width:100%;
}
.form-input:focus{border-color:var(--blue);box-shadow:0 0 0 3px rgba(37,99,235,.1)}
.form-input::placeholder{color:var(--text3)}
.form-select{appearance:none;background-image:url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 20 20'%3E%3Cpath fill='%2394A3B8' d='M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z'/%3E%3C/svg%3E");background-repeat:no-repeat;background-position:right 10px center;background-size:16px;padding-right:32px}

/* MODAL */
.modal-overlay{position:fixed;inset:0;background:rgba(0,0,0,.5);z-index:100;display:flex;align-items:center;justify-content:center;animation:fadeIn .2s ease}
.modal{background:var(--card);border-radius:16px;box-shadow:var(--shadow3);width:100%;max-width:600px;max-height:90vh;overflow-y:auto;animation:fadeUp .25s ease}
.modal-header{display:flex;align-items:center;justify-content:space-between;padding:20px 24px;border-bottom:1px solid var(--border)}
.modal-title{font-size:16px;font-weight:700}
.modal-close{width:32px;height:32px;border-radius:8px;border:1px solid var(--border);background:transparent;cursor:pointer;display:flex;align-items:center;justify-content:center;font-size:16px;color:var(--text2)}
.modal-close:hover{background:var(--red2);border-color:var(--red);color:var(--red3)}
.modal-body{padding:24px}
.modal-footer{padding:16px 24px;border-top:1px solid var(--border);display:flex;gap:10px;justify-content:flex-end}

/* TOAST */
.toast-wrap{position:fixed;bottom:24px;right:24px;z-index:200;display:flex;flex-direction:column;gap:8px}
.toast{
  display:flex;align-items:center;gap:10px;padding:12px 16px;
  border-radius:10px;box-shadow:var(--shadow3);font-size:13px;font-weight:500;
  max-width:320px;animation:fadeUp .3s ease;min-width:260px;
}
.toast-success{background:var(--green2);border:1px solid #A7F3D0;color:var(--green3)}
.toast-error{background:var(--red2);border:1px solid #FECACA;color:var(--red3)}
.toast-info{background:var(--blue4);border:1px solid #BFDBFE;color:var(--blue)}

/* MISC */
.divider{height:1px;background:var(--border);margin:16px 0}
.empty-state{text-align:center;padding:60px 20px;color:var(--text3)}
.empty-icon{font-size:48px;margin-bottom:12px;opacity:.5}
.spinner{width:24px;height:24px;border:3px solid var(--border);border-top-color:var(--blue);border-radius:50%;animation:spin .6s linear infinite}
.loading-overlay{display:flex;align-items:center;justify-content:center;padding:60px}
.tag{display:inline-flex;align-items:center;gap:4px;padding:2px 8px;border-radius:6px;font-size:11px;font-weight:600;background:var(--bg);color:var(--text2);border:1px solid var(--border)}
.img-preview{width:60px;height:60px;border-radius:8px;object-fit:cover;background:var(--bg);border:1px solid var(--border)}
.stat-mini{background:var(--bg);border-radius:8px;padding:10px 14px;border:1px solid var(--border)}
.stat-mini-val{font-size:18px;font-weight:700;color:var(--text)}
.stat-mini-lbl{font-size:11px;color:var(--text3);font-family:var(--mono)}
.alert-banner{border-radius:var(--r);border:1px solid;padding:12px 16px;display:flex;align-items:flex-start;gap:10px;margin-bottom:16px}
.alert-warning{background:var(--amber2);border-color:#FDE68A;color:var(--amber3)}
.alert-error{background:var(--red2);border-color:#FECACA;color:var(--red3)}
.filter-row{display:flex;gap:10px;flex-wrap:wrap;align-items:center;margin-bottom:16px}
.search-input-wrap{position:relative;flex:1;min-width:200px}
.search-input-wrap input{padding-left:34px}
.search-icon{position:absolute;left:10px;top:50%;transform:translateY(-50%);color:var(--text3);font-size:15px}

/* POS */
.pos-layout{display:grid;grid-template-columns:1fr 360px;gap:20px;height:calc(100vh - 140px)}
.pos-products{overflow-y:auto}
.pos-product-grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(160px,1fr));gap:12px}
.pos-prod-card{background:var(--card);border:1px solid var(--border);border-radius:var(--r);overflow:hidden;cursor:pointer;transition:all .2s}
.pos-prod-card:hover{border-color:var(--blue);box-shadow:var(--shadow2)}
.pos-prod-card.out{opacity:.5;cursor:not-allowed;pointer-events:none}
.pos-prod-img{width:100%;height:100px;object-fit:cover;background:var(--bg)}
.pos-prod-body{padding:10px}
.pos-prod-name{font-size:12px;font-weight:600;margin-bottom:3px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis}
.pos-prod-price{font-size:14px;font-weight:800;color:var(--blue)}
.pos-prod-stock{font-size:10px;color:var(--text3);font-family:var(--mono)}
.pos-cart{background:var(--card);border:1px solid var(--border);border-radius:var(--r);display:flex;flex-direction:column;overflow:hidden}
.pos-cart-header{padding:16px;border-bottom:1px solid var(--border);font-size:14px;font-weight:700}
.pos-cart-items{flex:1;overflow-y:auto;padding:12px}
.pos-cart-item{display:flex;align-items:center;gap:10px;padding:8px;border-radius:8px;margin-bottom:6px;background:var(--bg);border:1px solid var(--border)}
.pos-cart-item-name{font-size:12px;font-weight:600;flex:1}
.pos-cart-item-price{font-size:12px;font-weight:700;color:var(--blue)}
.pos-qty-btn{width:24px;height:24px;border-radius:6px;border:1px solid var(--border);background:var(--card);cursor:pointer;display:flex;align-items:center;justify-content:center;font-size:14px;font-weight:600;transition:all .15s}
.pos-qty-btn:hover{background:var(--blue);color:#fff;border-color:var(--blue)}
.pos-qty{font-size:13px;font-weight:700;min-width:20px;text-align:center;font-family:var(--mono)}
.pos-cart-footer{padding:16px;border-top:1px solid var(--border)}
.pos-total-row{display:flex;justify-content:space-between;font-size:13px;margin-bottom:8px;color:var(--text2)}
.pos-total-row.grand{font-size:18px;font-weight:800;color:var(--text);margin-top:8px;padding-top:8px;border-top:1px solid var(--border)}
.pos-btn-pagar{width:100%;margin-top:12px;padding:14px;font-size:15px}

/* BOLETA */
.boleta-overlay{position:fixed;inset:0;background:rgba(0,0,0,.6);z-index:1000;display:flex;align-items:center;justify-content:center;animation:fadeIn .2s ease}
.boleta-card{background:white;border-radius:16px;width:400px;max-height:90vh;overflow-y:auto;box-shadow:0 25px 50px rgba(0,0,0,.4);animation:fadeUp .3s ease}
.boleta-header{background:var(--blue);color:white;padding:24px;border-radius:16px 16px 0 0;text-align:center}
.boleta-empresa{font-size:16px;font-weight:800;letter-spacing:.5px;margin-bottom:2px}
.boleta-ruc{font-size:11px;opacity:.8;font-family:var(--mono);margin-bottom:8px}
.boleta-titulo{background:rgba(255,255,255,.15);border-radius:8px;padding:6px 12px;font-size:13px;font-weight:700;letter-spacing:1px;text-transform:uppercase;display:inline-block}
.boleta-body{padding:20px}
.boleta-row{display:flex;justify-content:space-between;align-items:center;padding:7px 0;border-bottom:1px dashed var(--border);font-size:13px}
.boleta-row:last-child{border-bottom:none}
.boleta-section{font-size:10px;font-weight:700;color:var(--text3);text-transform:uppercase;letter-spacing:1px;margin:14px 0 6px}
.boleta-total-row{display:flex;justify-content:space-between;padding:10px 0;font-size:16px;font-weight:800;color:var(--blue);border-top:2px solid var(--blue);margin-top:6px}
.boleta-footer{padding:0 20px 20px;display:flex;gap:8px}
@media print{.boleta-overlay{position:absolute}.boleta-footer{display:none}}

/* INVENTARIO MOVEMENT */
.movement-entry{display:flex;align-items:center;gap:12px;padding:12px;border-radius:8px;background:var(--bg);border:1px solid var(--border);margin-bottom:8px}
.mov-icon{width:36px;height:36px;border-radius:8px;display:flex;align-items:center;justify-content:center;font-size:16px;flex-shrink:0}
.mov-in{background:var(--green2);color:var(--green3)}
.mov-out{background:var(--red2);color:var(--red3)}
.mov-adj{background:var(--amber2);color:var(--amber3)}

/* LOGIN */
.login-bg{min-height:100vh;display:flex;align-items:center;justify-content:center;background:linear-gradient(135deg,#0F172A 0%,#1E293B 50%,#0F172A 100%);position:relative;overflow:hidden}
.login-bg::before{content:'';position:absolute;inset:0;background:radial-gradient(ellipse at 30% 50%,rgba(37,99,235,.15) 0%,transparent 60%),radial-gradient(ellipse at 70% 50%,rgba(139,92,246,.1) 0%,transparent 60%)}
.login-card{background:white;border-radius:20px;padding:40px;width:100%;max-width:400px;box-shadow:0 25px 50px rgba(0,0,0,.5);animation:fadeUp .4s ease;position:relative;z-index:1}
.login-header{text-align:center;margin-bottom:32px}
.login-logo{width:56px;height:56px;background:var(--blue);border-radius:16px;display:flex;align-items:center;justify-content:center;font-size:26px;margin:0 auto 16px}
.login-title{font-size:24px;font-weight:800;color:var(--text);margin-bottom:4px}
.login-sub{font-size:13px;color:var(--text3);font-family:var(--mono)}
.login-input{
  width:100%;background:var(--bg);border:1.5px solid var(--border2);color:var(--text);
  border-radius:10px;padding:12px 14px;font-size:14px;outline:none;transition:all .2s;
  margin-bottom:14px;
}
.login-input:focus{border-color:var(--blue);background:white;box-shadow:0 0 0 3px rgba(37,99,235,.1)}
.login-btn{
  width:100%;padding:13px;border-radius:10px;background:var(--blue);
  border:none;color:#fff;font-size:15px;font-weight:700;cursor:pointer;
  transition:all .2s;font-family:var(--font);margin-top:4px;
}
.login-btn:hover{background:var(--blue2);box-shadow:0 8px 24px rgba(37,99,235,.4);transform:translateY(-1px)}
.login-hint{text-align:center;font-size:11px;color:var(--text3);margin-top:16px;font-family:var(--mono)}

@media(max-width:1024px){
  .kpi-grid{grid-template-columns:1fr 1fr}
  .charts-row{grid-template-columns:1fr}
  .form-grid{grid-template-columns:1fr}
  .pos-layout{grid-template-columns:1fr}
}
@media(max-width:768px){
  .sidebar{width:64px}
  .nav-label,.logo-text,.sidebar-section,.user-name,.user-role{display:none}
  .kpi-grid{grid-template-columns:1fr 1fr}
}
`;

/* ── Mock Data ────────────────────────────────────────────────── */
const PRODUCTOS_MOCK = [
  {id:1,nombre:"iPhone 15 Pro",marca:"Apple",modelo:"A3101",precio:4599,stock:12,stockMinimo:3,categoria:"Gama Alta",descripcion:"Chip A17 Pro, Titanio, USB-C",procesador:"A17 Pro",ram:"8GB",almacenamiento:"256GB",camaras:"48MP + 12MP + 12MP",pantalla:'6.1" OLED',bateria:"3274mAh",so:"iOS 17",rating:4.9,ventas:48,activo:true,img:"https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=200&q=80"},
  {id:2,nombre:"Samsung Galaxy S24 Ultra",marca:"Samsung",modelo:"SM-S928B",precio:4299,stock:8,stockMinimo:3,categoria:"Gama Alta",descripcion:"S Pen integrado, 200MP",procesador:"Snapdragon 8 Gen 3",ram:"12GB",almacenamiento:"512GB",camaras:"200MP + 12MP + 50MP",pantalla:'6.8" AMOLED',bateria:"5000mAh",so:"Android 14",rating:4.8,ventas:36,activo:true,img:"https://images.unsplash.com/photo-1707398258011-5ad9a4f7f89e?w=200&q=80"},
  {id:3,nombre:"Xiaomi 14 Pro",marca:"Xiaomi",modelo:"MI-14PRO",precio:2999,stock:2,stockMinimo:3,categoria:"Gama Alta",descripcion:"Cámara Leica, MIUI 15",procesador:"Snapdragon 8 Gen 3",ram:"12GB",almacenamiento:"256GB",camaras:"50MP Leica",pantalla:'6.73" AMOLED',bateria:"4880mAh",so:"Android 14",rating:4.7,ventas:29,activo:true,img:"https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=200&q=80"},
  {id:4,nombre:"Google Pixel 9 Pro",marca:"Google",modelo:"GS1A3",precio:3499,stock:10,stockMinimo:3,categoria:"Gama Alta",descripcion:"IA Google, Android puro",procesador:"Tensor G4",ram:"16GB",almacenamiento:"256GB",camaras:"50MP + 48MP",pantalla:'6.3" OLED',bateria:"4700mAh",so:"Android 15",rating:4.8,ventas:22,activo:true,img:"https://images.unsplash.com/photo-1573100925118-870b8efc799d?w=200&q=80"},
  {id:5,nombre:"OnePlus 12",marca:"OnePlus",modelo:"PH2571",precio:2799,stock:20,stockMinimo:3,categoria:"Gama Alta",descripcion:"Carga 100W, OxygenOS 14",procesador:"Snapdragon 8 Gen 3",ram:"16GB",almacenamiento:"512GB",camaras:"50MP + 48MP",pantalla:'6.82" AMOLED',bateria:"5400mAh",so:"Android 14",rating:4.6,ventas:31,activo:true,img:"https://images.unsplash.com/photo-1583580599148-bba9a3f2db3e?w=200&q=80"},
  {id:6,nombre:"Motorola Edge 50 Pro",marca:"Motorola",modelo:"XT2451",precio:1899,stock:15,stockMinimo:3,categoria:"Gama Media",descripcion:"144Hz pOLED",procesador:"Snapdragon 7s Gen 2",ram:"12GB",almacenamiento:"256GB",camaras:"50MP + 13MP",pantalla:'6.7" pOLED',bateria:"4500mAh",so:"Android 14",rating:4.4,ventas:18,activo:true,img:"https://images.unsplash.com/photo-1600087626120-b9e93e58ba87?w=200&q=80"},
  {id:7,nombre:"Xiaomi Redmi Note 13 Pro",marca:"Xiaomi",modelo:"RN13PRO",precio:1299,stock:25,stockMinimo:5,categoria:"Gama Media",descripcion:"200MP, carga 67W",procesador:"Snapdragon 7s Gen 2",ram:"12GB",almacenamiento:"256GB",camaras:"200MP + 8MP",pantalla:'6.67" AMOLED',bateria:"5100mAh",so:"Android 13",rating:4.5,ventas:54,activo:true,img:"https://images.unsplash.com/photo-1591337676887-a217a6970a8a?w=200&q=80"},
  {id:8,nombre:"Realme C65",marca:"Realme",modelo:"RMX3910",precio:699,stock:0,stockMinimo:5,categoria:"Gama Básica",descripcion:"Entrada económica",procesador:"Helio G85",ram:"8GB",almacenamiento:"128GB",camaras:"50MP + 2MP",pantalla:'6.67" LCD',bateria:"5000mAh",so:"Android 14",rating:4.1,ventas:67,activo:true,img:"https://images.unsplash.com/photo-1614802414-8d1aa6ef9df3?w=200&q=80"},
];

const CLIENTES_MOCK = [
  {id:1,nombre:"María García López",email:"maria@gmail.com",telefono:"999 001 001",dni:"45678901",ciudad:"Lima",compras:8,total:28450,fechaReg:"2025-01-15",activo:true},
  {id:2,nombre:"Carlos Rodríguez",email:"carlos@hotmail.com",telefono:"999 002 002",dni:"45678902",ciudad:"Arequipa",compras:3,total:9200,fechaReg:"2025-02-20",activo:true},
  {id:3,nombre:"Ana Torres Mendoza",email:"ana.t@yahoo.com",telefono:"999 003 003",dni:"45678903",ciudad:"Trujillo",compras:12,total:45680,fechaReg:"2024-11-10",activo:true},
  {id:4,nombre:"Luis Huamán Quispe",email:"luis.h@gmail.com",telefono:"999 004 004",dni:"45678904",ciudad:"Cusco",compras:1,total:4599,fechaReg:"2025-05-01",activo:true},
  {id:5,nombre:"Sofía Vargas Castro",email:"sofia.v@gmail.com",telefono:"999 005 005",dni:"45678905",ciudad:"Lima",compras:5,total:18300,fechaReg:"2025-03-08",activo:true},
  {id:6,nombre:"Diego Morales",email:"diego.m@gmail.com",telefono:"999 006 006",dni:"45678906",ciudad:"Piura",compras:2,total:7200,fechaReg:"2025-04-22",activo:false},
];

const MOVIMIENTOS_MOCK = [
  {id:1,tipo:"entrada",producto:"iPhone 15 Pro",cantidad:20,antes:0,despues:20,motivo:"Compra a proveedor Apple",usuario:"Admin",fecha:"2025-05-28 09:00"},
  {id:2,tipo:"salida",producto:"Samsung Galaxy S24 Ultra",cantidad:2,antes:10,despues:8,motivo:"Venta #VTA-0042",usuario:"Admin",fecha:"2025-05-28 10:30"},
  {id:3,tipo:"salida",producto:"Xiaomi 14 Pro",cantidad:3,antes:5,despues:2,motivo:"Venta #VTA-0043",usuario:"Admin",fecha:"2025-05-28 11:15"},
  {id:4,tipo:"entrada",producto:"Xiaomi Redmi Note 13 Pro",cantidad:10,antes:15,despues:25,motivo:"Reabastecimiento",usuario:"Admin",fecha:"2025-05-27 14:00"},
  {id:5,tipo:"ajuste",producto:"Realme C65",cantidad:-5,antes:5,despues:0,motivo:"Ajuste por merma",usuario:"Admin",fecha:"2025-05-27 16:00"},
  {id:6,tipo:"salida",producto:"Google Pixel 9 Pro",cantidad:1,antes:11,despues:10,motivo:"Venta #VTA-0041",usuario:"Admin",fecha:"2025-05-26 13:00"},
];

const VENTAS_MOCK = [
  {id:"VTA-0042",cliente:"María García",productos:[{nombre:"Samsung Galaxy S24 Ultra",qty:2,precio:4299}],subtotal:8598,igv:1547.64,total:10145.64,metodo:"Tarjeta",fecha:"2025-05-28 10:30",estado:"Completada"},
  {id:"VTA-0041",cliente:"Ana Torres",productos:[{nombre:"Google Pixel 9 Pro",qty:1,precio:3499}],subtotal:3499,igv:629.82,total:4128.82,metodo:"Efectivo",fecha:"2025-05-26 13:00",estado:"Completada"},
  {id:"VTA-0040",cliente:"Luis Huamán",productos:[{nombre:"iPhone 15 Pro",qty:1,precio:4599}],subtotal:4599,igv:827.82,total:5426.82,metodo:"Yape",fecha:"2025-05-25 09:15",estado:"Completada"},
  {id:"VTA-0039",cliente:"Sofía Vargas",productos:[{nombre:"Xiaomi Redmi Note 13 Pro",qty:2,precio:1299}],subtotal:2598,igv:467.64,total:3065.64,metodo:"Transferencia",fecha:"2025-05-24 15:40",estado:"Completada"},
  {id:"VTA-0038",cliente:"Carlos Rodríguez",productos:[{nombre:"Motorola Edge 50 Pro",qty:1,precio:1899}],subtotal:1899,igv:341.82,total:2240.82,metodo:"Tarjeta",fecha:"2025-05-23 11:00",estado:"Completada"},
];

const VENTAS_MENSUALES = [
  {mes:"Ene",ventas:42,obj:40,ingreso:128000},
  {mes:"Feb",ventas:58,obj:55,ingreso:164000},
  {mes:"Mar",ventas:47,obj:52,ingreso:139000},
  {mes:"Abr",ventas:61,obj:58,ingreso:182000},
  {mes:"May",ventas:55,obj:60,ingreso:155000},
  {mes:"Jun",ventas:70,obj:65,ingreso:209000},
];

/* ── Toasts ───────────────────────────────────────────────────── */
let _addToast = null;
function ToastProvider() {
  const [toasts, setToasts] = useState([]);
  _addToast = (msg, type="success") => {
    const id = Date.now();
    setToasts(t => [...t, {id,msg,type}]);
    setTimeout(() => setToasts(t => t.filter(x => x.id !== id)), 3500);
  };
  const icons = {success:"✅",error:"❌",info:"ℹ️"};
  return (
    <div className="toast-wrap">
      {toasts.map(t => (
        <div key={t.id} className={`toast toast-${t.type}`}>
          <span>{icons[t.type]}</span> {t.msg}
        </div>
      ))}
    </div>
  );
}
const toast = (msg, type="success") => _addToast?.(msg, type);

/* ── Charts ───────────────────────────────────────────────────── */
function BarChart({ data, color="#2563EB", color2="#10B981" }) {
  const max = Math.max(...data.map(d => Math.max(d.ventas, d.obj)));
  return (
    <div className="chart-area">
      <div className="chart-bars">
        {data.map((d, i) => (
          <div key={i} className="chart-bar-wrap">
            <div className="chart-bar-inner">
              <div style={{display:"flex",gap:"2px",alignItems:"flex-end",height:"100%"}}>
                <div className="chart-bar" style={{height:`${(d.ventas/max)*100}%`,background:color,flex:1}} title={`Ventas: ${d.ventas}`}/>
                <div className="chart-bar" style={{height:`${(d.obj/max)*100}%`,background:color2,flex:1,opacity:.5}} title={`Obj: ${d.obj}`}/>
              </div>
            </div>
            <span className="chart-bar-lbl">{d.mes}</span>
          </div>
        ))}
      </div>
      <div style={{display:"flex",gap:12,fontSize:12,color:"var(--text3)"}}>
        <span style={{display:"flex",alignItems:"center",gap:4}}><span style={{width:8,height:8,borderRadius:2,background:color,display:"inline-block"}}/>Ventas</span>
        <span style={{display:"flex",alignItems:"center",gap:4}}><span style={{width:8,height:8,borderRadius:2,background:color2,opacity:.6,display:"inline-block"}}/>Objetivo</span>
      </div>
    </div>
  );
}

function LineChart({ data }) {
  const vals = data.map(d => d.ingreso);
  const max = Math.max(...vals), min = Math.min(...vals);
  const W=300, H=140, pad=16;
  const pts = data.map((d,i) => {
    const x = pad + (i/(data.length-1))*(W-pad*2);
    const y = H-pad - ((d.ingreso-min)/(max-min||1))*(H-pad*2);
    return [x,y];
  });
  const d = pts.map((p,i) => (i===0?`M${p[0]},${p[1]}`:`L${p[0]},${p[1]}`)).join(" ");
  const area = `${d} L${pts[pts.length-1][0]},${H} L${pts[0][0]},${H} Z`;
  return (
    <div className="chart-area">
      <svg viewBox={`0 0 ${W} ${H}`} className="chart-line-svg">
        <defs>
          <linearGradient id="lineg" x1="0" y1="0" x2="0" y2="1">
            <stop offset="0%" stopColor="#2563EB" stopOpacity="0.2"/>
            <stop offset="100%" stopColor="#2563EB" stopOpacity="0"/>
          </linearGradient>
        </defs>
        <path d={area} fill="url(#lineg)"/>
        <path d={d} fill="none" stroke="#2563EB" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round"/>
        {pts.map(([x,y],i) => <circle key={i} cx={x} cy={y} r="4" fill="#2563EB" stroke="white" strokeWidth="2"/>)}
      </svg>
      <div style={{fontSize:11,color:"var(--text3)",fontFamily:"var(--mono)",textAlign:"center",marginTop:4}}>Ingresos mensuales (S/.)</div>
    </div>
  );
}

function DonutChart({ segments }) {
  const total = segments.reduce((s,x) => s+x.val, 0);
  let angle = -90;
  const paths = segments.map(seg => {
    const pct = seg.val/total;
    const a1 = angle * Math.PI/180;
    const a2 = (angle + pct*360) * Math.PI/180;
    const x1 = 50 + 38*Math.cos(a1), y1 = 50 + 38*Math.sin(a1);
    const x2 = 50 + 38*Math.cos(a2), y2 = 50 + 38*Math.sin(a2);
    const large = pct > 0.5 ? 1 : 0;
    const path = `M50,50 L${x1},${y1} A38,38 0 ${large} 1 ${x2},${y2} Z`;
    angle += pct*360;
    return {path, color:seg.color, label:seg.label, val:seg.val, pct};
  });
  return (
    <div className="doughnut-wrap">
      <svg viewBox="0 0 100 100" width="120" height="120">
        {paths.map((p,i) => <path key={i} d={p.path} fill={p.color}/>)}
        <circle cx="50" cy="50" r="25" fill="white"/>
        <text x="50" y="50" textAnchor="middle" dy=".35em" fontSize="10" fontWeight="700" fill="#1E293B">{total}</text>
      </svg>
      <div style={{display:"flex",flexDirection:"column",gap:6}}>
        {paths.map((p,i) => (
          <div key={i} className="legend-item">
            <div className="legend-dot" style={{background:p.color}}/>
            <span>{p.label}</span>
            <span style={{marginLeft:"auto",fontWeight:700}}>{p.val}</span>
          </div>
        ))}
      </div>
    </div>
  );
}

/* ── Utilidad: exportar a CSV (abre en Excel) ─────────────────── */
function exportCSV(rows, filename) {
  if (!rows || rows.length === 0) { toast("Sin datos para exportar", "error"); return; }
  const headers = Object.keys(rows[0]);
  const csvContent = [
    headers.join(","),
    ...rows.map(row =>
      headers.map(h => {
        const val = row[h] ?? "";
        const str = String(val).replace(/"/g, '""');
        return str.includes(",") || str.includes("\n") || str.includes('"') ? `"${str}"` : str;
      }).join(",")
    )
  ].join("\n");
  const blob = new Blob(["﻿" + csvContent], { type: "text/csv;charset=utf-8;" });
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url; a.download = filename; a.click();
  URL.revokeObjectURL(url);
  toast(`📥 ${filename} descargado`);
}

/* ── Components ───────────────────────────────────────────────── */
function StockBadge({stock, min=3}) {
  if (stock === 0) return <span className="badge badge-red">Agotado</span>;
  if (stock <= min) return <span className="badge badge-amber">Stock bajo: {stock}</span>;
  return <span className="badge badge-green">{stock} unid.</span>;
}

function EstadoBadge({estado}) {
  const map = {
    Completada:"badge-green", Pendiente:"badge-amber", Cancelada:"badge-red",
    Activo:"badge-green", Inactivo:"badge-gray",
    "Gama Alta":"badge-blue","Gama Media":"badge-purple","Gama Básica":"badge-gray"
  };
  return <span className={`badge ${map[estado]||"badge-gray"}`}>{estado}</span>;
}

function SearchInput({value, onChange, placeholder="Buscar..."}) {
  return (
    <div className="search-input-wrap">
      <span className="search-icon">🔍</span>
      <input className="form-input" style={{paddingLeft:34}} placeholder={placeholder} value={value} onChange={e=>onChange(e.target.value)}/>
    </div>
  );
}

/* ════════════════════════════════════════════════════════════════
   MODULES
════════════════════════════════════════════════════════════════ */

/* ── DASHBOARD ────────────────────────────────────────────────── */
function Dashboard({productos}) {
  const alertas = productos.filter(p => p.stock <= p.stockMinimo);
  const totalStock = productos.reduce((s,p) => s+p.stock, 0);
  const topProds = [...productos].sort((a,b) => b.ventas-a.ventas).slice(0,5);
  const categories = [
    {label:"Gama Alta", val:productos.filter(p=>p.categoria==="Gama Alta").length, color:"#2563EB"},
    {label:"Gama Media", val:productos.filter(p=>p.categoria==="Gama Media").length, color:"#8B5CF6"},
    {label:"Gama Básica", val:productos.filter(p=>p.categoria==="Gama Básica").length, color:"#10B981"},
  ];

  return (
    <div style={{animation:"fadeUp .4s ease"}}>
      {alertas.length > 0 && (
        <div className="alert-banner alert-warning" style={{marginBottom:20}}>
          ⚠️ <strong>{alertas.length} producto(s)</strong> con stock bajo o agotado. Revisar inventario.
        </div>
      )}
      <div className="kpi-grid">
        {[
          {label:"Ventas Hoy",val:"23",change:"+12%",up:true,icon:"📈",cls:"kpi-blue"},
          {label:"Ingresos Mes",val:`S/ ${(155000).toLocaleString("es-PE")}`,change:"+8.3%",up:true,icon:"💰",cls:"kpi-green"},
          {label:"Productos en Stock",val:totalStock,change:`${productos.length} modelos`,up:true,icon:"📦",cls:"kpi-amber"},
          {label:"Alertas Stock",val:alertas.length,change:alertas.length>0?"Requiere acción":"OK",up:alertas.length===0,icon:"⚠️",cls:alertas.length>0?"kpi-red":"kpi-green"},
        ].map((k,i)=>(
          <div key={i} className={`kpi-card ${k.cls}`} style={{animationDelay:`${i*.05}s`}}>
            <div className="kpi-icon-wrap">{k.icon}</div>
            <div style={{flex:1}}>
              <div className="kpi-label">{k.label}</div>
              <div className="kpi-val">{k.val}</div>
              <div className={`kpi-change ${k.up?"kpi-up":"kpi-down"}`}>{k.up?"▲":"▼"} {k.change}</div>
            </div>
          </div>
        ))}
      </div>

      <div className="charts-row">
        <div className="card">
          <div className="card-header">
            <span className="card-title">📊 Ventas Mensuales vs Objetivo</span>
            <span className="badge badge-blue">2025</span>
          </div>
          <div className="card-body"><BarChart data={VENTAS_MENSUALES}/></div>
        </div>
        <div className="card">
          <div className="card-header"><span className="card-title">🏷️ Por Categoría</span></div>
          <div className="card-body"><DonutChart segments={categories}/></div>
        </div>
      </div>

      <div style={{display:"grid",gridTemplateColumns:"1fr 1fr",gap:16}}>
        <div className="card">
          <div className="card-header">
            <span className="card-title">🏆 Top Productos — Más Vendidos</span>
          </div>
          <div className="card-body" style={{padding:0}}>
            <div className="table-wrap">
              <table>
                <thead><tr><th>#</th><th>Producto</th><th>Marca</th><th>Ventas</th><th>Rating</th></tr></thead>
                <tbody>
                  {topProds.map((p,i)=>(
                    <tr key={p.id}>
                      <td><span style={{fontWeight:800,color:i===0?"#F59E0B":i===1?"#94A3B8":i===2?"#92400E":"var(--text3)"}}>{i===0?"🥇":i===1?"🥈":i===2?"🥉":`#${i+1}`}</span></td>
                      <td><span style={{fontWeight:600}}>{p.nombre}</span></td>
                      <td><span className="tag">{p.marca}</span></td>
                      <td><strong>{p.ventas}</strong></td>
                      <td>⭐ {p.rating}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="card-header">
            <span className="card-title">🚨 Alertas de Stock Bajo</span>
            {alertas.length>0 && <span className="badge badge-red">{alertas.length} alertas</span>}
          </div>
          <div className="card-body" style={{padding:0}}>
            {alertas.length===0 ? (
              <div style={{padding:20,textAlign:"center",color:"var(--green3)"}}>✅ Todo el inventario está en orden</div>
            ) : (
              <div className="table-wrap">
                <table>
                  <thead><tr><th>Producto</th><th>Stock</th><th>Mínimo</th><th>Estado</th></tr></thead>
                  <tbody>
                    {alertas.map(p=>(
                      <tr key={p.id}>
                        <td>{p.nombre}</td>
                        <td><strong style={{color:p.stock===0?"var(--red3)":"var(--amber3)"}}>{p.stock}</strong></td>
                        <td>{p.stockMinimo}</td>
                        <td><StockBadge stock={p.stock} min={p.stockMinimo}/></td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

/* ── PRODUCTOS ────────────────────────────────────────────────── */
function Productos({productos, setProductos}) {
  const [search, setSearch] = useState("");
  const [marcaFilter, setMarcaFilter] = useState("");
  const [catFilter, setCatFilter] = useState("");
  const [modalOpen, setModalOpen] = useState(false);
  const [editProd, setEditProd] = useState(null);
  const [detalle, setDetalle] = useState(null);
  const [form, setForm] = useState({nombre:"",marca:"Apple",modelo:"",precio:"",stock:"",stockMinimo:3,categoria:"Gama Alta",descripcion:"",procesador:"",ram:"",almacenamiento:"",camaras:"",pantalla:"",bateria:"",so:""});

  const marcas = [...new Set(productos.map(p=>p.marca))];
  const filtered = productos.filter(p => {
    const q = search.toLowerCase();
    return (!q || p.nombre.toLowerCase().includes(q) || p.marca.toLowerCase().includes(q) || p.modelo.toLowerCase().includes(q))
      && (!marcaFilter || p.marca===marcaFilter)
      && (!catFilter || p.categoria===catFilter);
  });

  const openAdd = () => { setEditProd(null); setForm({nombre:"",marca:"Apple",modelo:"",precio:"",stock:"",stockMinimo:3,categoria:"Gama Alta",descripcion:"",procesador:"",ram:"",almacenamiento:"",camaras:"",pantalla:"",bateria:"",so:""}); setModalOpen(true); };
  const openEdit = (p) => { setEditProd(p); setForm({...p}); setModalOpen(true); };

  const save = () => {
    const precioNum = Number(form.precio);
    const stockNum = Number(form.stock);
    const stockMinNum = Number(form.stockMinimo);
    if (!form.nombre?.trim()) { toast("El nombre del producto es obligatorio", "error"); return; }
    if (!form.marca?.trim()) { toast("La marca es obligatoria", "error"); return; }
    if (!form.modelo?.trim()) { toast("El modelo es obligatorio", "error"); return; }
    if (form.precio === "" || isNaN(precioNum) || precioNum <= 0) { toast("El precio debe ser mayor a S/ 0.00", "error"); return; }
    if (form.stock === "" || isNaN(stockNum) || stockNum < 0) { toast("El stock no puede ser negativo", "error"); return; }
    if (editProd) {
      setProductos(ps => ps.map(p => p.id===editProd.id ? {...p,...form,precio:precioNum,stock:stockNum,stockMinimo:stockMinNum} : p));
      toast(`✏️ ${form.nombre} actualizado`);
    } else {
      const np = {...form,id:Date.now(),precio:precioNum,stock:stockNum,stockMinimo:stockMinNum,ventas:0,rating:0,activo:true,img:""};
      setProductos(ps => [...ps, np]);
      toast(`➕ ${form.nombre} registrado`);
    }
    setModalOpen(false);
  };

  const toggle = (p) => {
    setProductos(ps => ps.map(x => x.id===p.id ? {...x,activo:!x.activo} : x));
    toast(`${p.activo?"❌ Desactivado":"✅ Activado"}: ${p.nombre}`);
  };

  return (
    <div style={{animation:"fadeUp .4s ease"}}>
      <div className="page-header">
        <div><div className="page-title">Gestión de Productos</div><div className="page-sub">{filtered.length} de {productos.length} productos</div></div>
        <div style={{display:"flex",gap:8}}>
          <button className="btn btn-secondary" onClick={()=>exportCSV(filtered.map(p=>({Marca:p.marca,Modelo:p.modelo,Categoria:p.categoria,"Precio (S/)":p.precio,Stock:p.stock,"Stock Minimo":p.stockMinimo,Ventas:p.ventas,Estado:p.activo?"Activo":"Inactivo"})),`FinTechMovil_Productos_${new Date().toLocaleDateString("es-PE").replace(/\//g,"-")}.csv`)}>📥 Exportar CSV</button>
          <button className="btn btn-primary" onClick={openAdd}>➕ Nuevo Producto</button>
        </div>
      </div>

      <div className="card">
        <div className="card-body" style={{paddingBottom:0}}>
          <div className="filter-row">
            <SearchInput value={search} onChange={setSearch} placeholder="Buscar por nombre, marca o modelo..."/>
            <select className="form-input form-select" style={{width:160}} value={marcaFilter} onChange={e=>setMarcaFilter(e.target.value)}>
              <option value="">Todas las marcas</option>
              {marcas.map(m=><option key={m}>{m}</option>)}
            </select>
            <select className="form-input form-select" style={{width:160}} value={catFilter} onChange={e=>setCatFilter(e.target.value)}>
              <option value="">Todas las categorías</option>
              <option>Gama Alta</option><option>Gama Media</option><option>Gama Básica</option>
            </select>
            {(search||marcaFilter||catFilter) && <button className="btn btn-secondary btn-sm" onClick={()=>{setSearch("");setMarcaFilter("");setCatFilter("");}}>✕ Limpiar</button>}
          </div>
        </div>
        <div className="table-wrap">
          <table>
            <thead><tr><th>Foto</th><th>Producto</th><th>Marca</th><th>Categoría</th><th>Precio</th><th>Stock</th><th>Ventas</th><th>Estado</th><th>Acciones</th></tr></thead>
            <tbody>
              {filtered.map(p=>(
                <tr key={p.id}>
                  <td><img src={p.img||"https://via.placeholder.com/60"} alt="" className="img-preview" onError={e=>e.target.style.display="none"}/></td>
                  <td>
                    <div style={{fontWeight:600}}>{p.nombre}</div>
                    <div style={{fontSize:11,color:"var(--text3)",fontFamily:"var(--mono)"}}>{p.modelo}</div>
                  </td>
                  <td><span className="tag">{p.marca}</span></td>
                  <td><EstadoBadge estado={p.categoria}/></td>
                  <td><strong>S/ {p.precio.toLocaleString("es-PE")}</strong><div style={{fontSize:10,color:"var(--text3)"}}>+IGV: S/ {(p.precio*1.18).toLocaleString("es-PE",{maximumFractionDigits:0})}</div></td>
                  <td><StockBadge stock={p.stock} min={p.stockMinimo}/></td>
                  <td><strong>{p.ventas}</strong></td>
                  <td><EstadoBadge estado={p.activo?"Activo":"Inactivo"}/></td>
                  <td>
                    <div style={{display:"flex",gap:4}}>
                      <button className="btn-icon" title="Ver detalle" onClick={()=>setDetalle(p)}>👁</button>
                      <button className="btn-icon" title="Editar" onClick={()=>openEdit(p)}>✏️</button>
                      <button className="btn-icon" title={p.activo?"Desactivar":"Activar"} onClick={()=>toggle(p)}>{p.activo?"🔴":"🟢"}</button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {filtered.length===0 && <div className="empty-state"><div className="empty-icon">📱</div><p>Sin resultados</p></div>}
        </div>
      </div>

      {/* Modal Agregar/Editar */}
      {modalOpen && (
        <div className="modal-overlay" onClick={e=>e.target===e.currentTarget&&setModalOpen(false)}>
          <div className="modal">
            <div className="modal-header">
              <span className="modal-title">{editProd?"✏️ Editar Producto":"➕ Nuevo Producto"}</span>
              <button className="modal-close" onClick={()=>setModalOpen(false)}>✕</button>
            </div>
            <div className="modal-body">
              <div className="form-grid">
                <div className="form-group full"><label className="form-label">Nombre del Producto *</label><input className="form-input" value={form.nombre} onChange={e=>setForm({...form,nombre:e.target.value})} placeholder="Ej: iPhone 15 Pro"/></div>
                <div className="form-group"><label className="form-label">Marca *</label>
                  <select className="form-input form-select" value={form.marca} onChange={e=>setForm({...form,marca:e.target.value})}>
                    {["Apple","Samsung","Xiaomi","Google","OnePlus","Huawei","Motorola","Nokia","Realme","Oppo"].map(m=><option key={m}>{m}</option>)}
                  </select></div>
                <div className="form-group"><label className="form-label">Modelo</label><input className="form-input" value={form.modelo} onChange={e=>setForm({...form,modelo:e.target.value})} placeholder="Ej: A3101"/></div>
                <div className="form-group"><label className="form-label">Precio Base (sin IGV) *</label><input className="form-input" type="number" value={form.precio} onChange={e=>setForm({...form,precio:e.target.value})} placeholder="0.00"/></div>
                <div className="form-group"><label className="form-label">Stock Inicial *</label><input className="form-input" type="number" value={form.stock} onChange={e=>setForm({...form,stock:e.target.value})} placeholder="0"/></div>
                <div className="form-group"><label className="form-label">Stock Mínimo</label><input className="form-input" type="number" value={form.stockMinimo} onChange={e=>setForm({...form,stockMinimo:e.target.value})}/></div>
                <div className="form-group"><label className="form-label">Categoría</label>
                  <select className="form-input form-select" value={form.categoria} onChange={e=>setForm({...form,categoria:e.target.value})}>
                    <option>Gama Alta</option><option>Gama Media</option><option>Gama Básica</option>
                  </select></div>
                <div className="form-group full"><label className="form-label">Descripción</label><input className="form-input" value={form.descripcion||""} onChange={e=>setForm({...form,descripcion:e.target.value})}/></div>
              </div>
              <div className="divider"/>
              <div style={{fontSize:12,fontWeight:700,color:"var(--text3)",marginBottom:12,fontFamily:"var(--mono)"}}>ESPECIFICACIONES TÉCNICAS</div>
              <div className="form-grid-3">
                {[["procesador","Procesador"],["ram","Memoria RAM"],["almacenamiento","Almacenamiento"],["camaras","Cámaras"],["pantalla","Pantalla"],["bateria","Batería"],["so","Sistema Operativo"]].map(([k,l])=>(
                  <div key={k} className="form-group"><label className="form-label">{l}</label><input className="form-input" value={form[k]||""} onChange={e=>setForm({...form,[k]:e.target.value})} placeholder={l}/></div>
                ))}
              </div>
            </div>
            <div className="modal-footer">
              <button className="btn btn-secondary" onClick={()=>setModalOpen(false)}>Cancelar</button>
              <button className="btn btn-primary" onClick={save}>{editProd?"💾 Guardar cambios":"➕ Registrar"}</button>
            </div>
          </div>
        </div>
      )}

      {/* Modal Detalle */}
      {detalle && (
        <div className="modal-overlay" onClick={e=>e.target===e.currentTarget&&setDetalle(null)}>
          <div className="modal">
            <div className="modal-header">
              <span className="modal-title">📱 {detalle.nombre}</span>
              <button className="modal-close" onClick={()=>setDetalle(null)}>✕</button>
            </div>
            <div className="modal-body">
              <div style={{display:"grid",gridTemplateColumns:"140px 1fr",gap:16,marginBottom:20}}>
                <img src={detalle.img} alt="" style={{width:140,height:140,borderRadius:10,objectFit:"cover",background:"var(--bg)"}} onError={e=>{e.target.src="";e.target.style.background="var(--bg)"}}/>
                <div>
                  <div style={{fontSize:11,color:"var(--text3)",fontFamily:"var(--mono)",marginBottom:4}}>{detalle.marca} · {detalle.modelo}</div>
                  <div style={{fontSize:20,fontWeight:800,marginBottom:8}}>{detalle.nombre}</div>
                  <div style={{display:"flex",gap:8,flexWrap:"wrap",marginBottom:8}}>
                    <EstadoBadge estado={detalle.categoria}/>
                    <StockBadge stock={detalle.stock} min={detalle.stockMinimo}/>
                  </div>
                  <div style={{fontSize:24,fontWeight:800,color:"var(--blue)"}}>S/ {detalle.precio.toLocaleString("es-PE")}</div>
                  <div style={{fontSize:11,color:"var(--text3)"}}>Con IGV: S/ {(detalle.precio*1.18).toFixed(2)}</div>
                </div>
              </div>
              <div style={{display:"grid",gridTemplateColumns:"1fr 1fr",gap:10}}>
                {[["Procesador",detalle.procesador],["RAM",detalle.ram],["Almacenamiento",detalle.almacenamiento],["Cámaras",detalle.camaras],["Pantalla",detalle.pantalla],["Batería",detalle.bateria],["Sistema Operativo",detalle.so],["Rating",`⭐ ${detalle.rating}`],["Ventas totales",detalle.ventas],["Descripción",detalle.descripcion]].filter(([,v])=>v).map(([k,v])=>(
                  <div key={k} className="stat-mini">
                    <div className="stat-mini-lbl">{k}</div>
                    <div className="stat-mini-val" style={{fontSize:13}}>{v}</div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

/* ── INVENTARIO ───────────────────────────────────────────────── */
function Inventario({productos, setProductos}) {
  const [movimientos, setMovimientos] = useState(MOVIMIENTOS_MOCK);
  const [modalOpen, setModalOpen] = useState(false);
  const [form, setForm] = useState({productoId:"",tipo:"entrada",cantidad:"",motivo:""});
  const [search, setSearch] = useState("");

  const registrarMovimiento = () => {
    if (!form.productoId || !form.cantidad || !form.motivo) { toast("Complete todos los campos","error"); return; }
    const prod = productos.find(p=>p.id===+form.productoId);
    if (!prod) return;
    const cant = +form.cantidad * (form.tipo==="salida"?-1:1);
    const nuevo = prod.stock + cant;
    if (nuevo < 0) { toast("Stock insuficiente para esta operación","error"); return; }
    const mov = {id:Date.now(),tipo:form.tipo,producto:prod.nombre,cantidad:Math.abs(cant),antes:prod.stock,despues:nuevo,motivo:form.motivo,usuario:"Admin",fecha:new Date().toLocaleString("es-PE")};
    setMovimientos(m => [mov,...m]);
    setProductos(ps => ps.map(p => p.id===prod.id ? {...p,stock:nuevo} : p));
    toast(`${form.tipo==="entrada"?"📥":"📤"} Movimiento registrado: ${prod.nombre}`);
    setModalOpen(false);
    setForm({productoId:"",tipo:"entrada",cantidad:"",motivo:""});
  };

  const filteredMovs = movimientos.filter(m => !search || m.producto.toLowerCase().includes(search.toLowerCase()));

  const movIcon = {entrada:{cls:"mov-in",icon:"📥"},salida:{cls:"mov-out",icon:"📤"},ajuste:{cls:"mov-adj",icon:"🔧"}};

  return (
    <div style={{animation:"fadeUp .4s ease"}}>
      <div className="page-header">
        <div><div className="page-title">Control de Inventario</div><div className="page-sub">Stock actual y movimientos del almacén</div></div>
        <button className="btn btn-primary" onClick={()=>setModalOpen(true)}>📦 Registrar Movimiento</button>
      </div>

      <div className="kpi-grid" style={{gridTemplateColumns:"repeat(4,1fr)"}}>
        {[
          {label:"Total Productos",val:productos.length,icon:"📱",cls:"kpi-blue"},
          {label:"Unidades en Stock",val:productos.reduce((s,p)=>s+p.stock,0),icon:"📦",cls:"kpi-green"},
          {label:"Stock Bajo / Agotado",val:productos.filter(p=>p.stock<=p.stockMinimo).length,icon:"⚠️",cls:"kpi-amber"},
          {label:"Valor del Inventario",val:`S/ ${(productos.reduce((s,p)=>s+p.precio*p.stock,0)).toLocaleString("es-PE",{maximumFractionDigits:0})}`,icon:"💰",cls:"kpi-purple"},
        ].map((k,i)=>(
          <div key={i} className={`kpi-card ${k.cls}`}>
            <div className="kpi-icon-wrap">{k.icon}</div>
            <div><div className="kpi-label">{k.label}</div><div className="kpi-val" style={{fontSize:20}}>{k.val}</div></div>
          </div>
        ))}
      </div>

      <div style={{display:"grid",gridTemplateColumns:"1fr 1fr",gap:16}}>
        <div className="card">
          <div className="card-header"><span className="card-title">📊 Estado del Stock por Producto</span></div>
          <div className="table-wrap">
            <table>
              <thead><tr><th>Producto</th><th>Marca</th><th>Stock</th><th>Mínimo</th><th>Estado</th></tr></thead>
              <tbody>
                {productos.map(p=>(
                  <tr key={p.id}>
                    <td style={{maxWidth:180,whiteSpace:"normal"}}>{p.nombre}</td>
                    <td><span className="tag">{p.marca}</span></td>
                    <td>
                      <div style={{display:"flex",alignItems:"center",gap:6}}>
                        <div style={{width:60,height:6,background:"var(--border)",borderRadius:3,overflow:"hidden"}}>
                          <div style={{height:"100%",background:p.stock===0?"var(--red)":p.stock<=p.stockMinimo?"var(--amber)":"var(--green)",width:`${Math.min(100,(p.stock/Math.max(p.stock,p.stockMinimo*3))*100)}%`}}/>
                        </div>
                        <strong>{p.stock}</strong>
                      </div>
                    </td>
                    <td>{p.stockMinimo}</td>
                    <td><StockBadge stock={p.stock} min={p.stockMinimo}/></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        <div className="card">
          <div className="card-header">
            <span className="card-title">📋 Historial de Movimientos</span>
            <SearchInput value={search} onChange={setSearch} placeholder="Filtrar..."/>
          </div>
          <div style={{padding:12,maxHeight:400,overflowY:"auto"}}>
            {filteredMovs.map(m=>(
              <div key={m.id} className="movement-entry">
                <div className={`mov-icon ${movIcon[m.tipo]?.cls}`}>{movIcon[m.tipo]?.icon}</div>
                <div style={{flex:1}}>
                  <div style={{fontSize:13,fontWeight:600}}>{m.producto}</div>
                  <div style={{fontSize:11,color:"var(--text3)"}}>{m.motivo}</div>
                  <div style={{fontSize:10,color:"var(--text3)",fontFamily:"var(--mono)"}}>{m.fecha}</div>
                </div>
                <div style={{textAlign:"right"}}>
                  <div style={{fontWeight:700,color:m.tipo==="entrada"?"var(--green3)":m.tipo==="salida"?"var(--red3)":"var(--amber3)"}}>
                    {m.tipo==="entrada"?"+":"-"}{m.cantidad} uds
                  </div>
                  <div style={{fontSize:10,color:"var(--text3)",fontFamily:"var(--mono)"}}>{m.antes}→{m.despues}</div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>

      {modalOpen && (
        <div className="modal-overlay" onClick={e=>e.target===e.currentTarget&&setModalOpen(false)}>
          <div className="modal" style={{maxWidth:480}}>
            <div className="modal-header">
              <span className="modal-title">📦 Registrar Movimiento de Inventario</span>
              <button className="modal-close" onClick={()=>setModalOpen(false)}>✕</button>
            </div>
            <div className="modal-body">
              <div className="form-grid">
                <div className="form-group full">
                  <label className="form-label">Producto *</label>
                  <select className="form-input form-select" value={form.productoId} onChange={e=>setForm({...form,productoId:e.target.value})}>
                    <option value="">-- Seleccionar producto --</option>
                    {productos.map(p=><option key={p.id} value={p.id}>{p.nombre} (Stock: {p.stock})</option>)}
                  </select>
                </div>
                <div className="form-group">
                  <label className="form-label">Tipo de Movimiento *</label>
                  <select className="form-input form-select" value={form.tipo} onChange={e=>setForm({...form,tipo:e.target.value})}>
                    <option value="entrada">📥 Entrada</option>
                    <option value="salida">📤 Salida</option>
                    <option value="ajuste">🔧 Ajuste</option>
                  </select>
                </div>
                <div className="form-group">
                  <label className="form-label">Cantidad *</label>
                  <input className="form-input" type="number" min="1" value={form.cantidad} onChange={e=>setForm({...form,cantidad:e.target.value})} placeholder="0"/>
                </div>
                <div className="form-group full">
                  <label className="form-label">Motivo / Referencia *</label>
                  <input className="form-input" value={form.motivo} onChange={e=>setForm({...form,motivo:e.target.value})} placeholder="Ej: Compra a proveedor, Venta #001..."/>
                </div>
              </div>
            </div>
            <div className="modal-footer">
              <button className="btn btn-secondary" onClick={()=>setModalOpen(false)}>Cancelar</button>
              <button className="btn btn-primary" onClick={registrarMovimiento}>✅ Registrar</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

/* ── VENTAS / POS ─────────────────────────────────────────────── */
function Ventas({productos, setProductos}) {
  const [tab, setTab] = useState("pos");
  const [cart, setCart] = useState([]);
  const [ventas, setVentas] = useState(VENTAS_MOCK);
  const [cliente, setCliente] = useState("");
  const [dniCliente, setDniCliente] = useState("");
  const [buscandoDNIPos, setBuscandoDNIPos] = useState(false);
  const [metodo, setMetodo] = useState("Efectivo");
  const [paying, setPaying] = useState(false);
  const [search, setSearch] = useState("");
  const [marcaFilter, setMarcaFilter] = useState("");
  const [boletaVenta, setBoletaVenta] = useState(null);

  const subtotal = cart.reduce((s,i) => s+i.precio*i.qty, 0);
  const igv = subtotal * 0.18;
  const total = subtotal * 1.18;

  const addToCart = (p) => {
    const prodActual = productos.find(pr=>pr.id===p.id);
    if (!prodActual || prodActual.stock === 0) { toast("Producto agotado — sin stock disponible","error"); return; }
    const inCart = cart.find(i=>i.id===p.id);
    if (inCart && inCart.qty >= prodActual.stock) { toast(`Stock máximo alcanzado (${prodActual.stock} unid.)`, "error"); return; }
    setCart(c => {
      const ex = c.find(i=>i.id===p.id);
      if (ex) return c.map(i=>i.id===p.id?{...i,qty:i.qty+1}:i);
      return [...c,{...p,qty:1}];
    });
  };

  const updateQty = (id, qty) => {
    if (qty<=0) { setCart(c=>c.filter(i=>i.id!==id)); return; }
    const prod = productos.find(p=>p.id===id);
    if (prod && qty > prod.stock) { toast(`Stock máximo disponible: ${prod.stock} unidad(es)`,"error"); return; }
    setCart(c=>c.map(i=>i.id===id?{...i,qty}:i));
  };

  const buscarDNIPos = async () => {
    if (dniCliente.length !== 8) { toast("El DNI debe tener 8 dígitos","error"); return; }
    setBuscandoDNIPos(true);
    try {
      const nombre = await consultarDNI(dniCliente);
      setCliente(nombre);
      toast(`✅ ${nombre}`);
    } catch(e) {
      toast(`DNI no encontrado (${e.message})`,"error");
    } finally {
      setBuscandoDNIPos(false);
    }
  };

  const procesarVenta = () => {
    if (cart.length===0) { toast("Carrito vacío","error"); return; }
    if (!cliente.trim()) { toast("Ingrese el cliente (busca por DNI o escríbelo)","error"); return; }
    for (const item of cart) {
      const prod = productos.find(p=>p.id===item.id);
      if (!prod) { toast(`Producto no encontrado: ${item.nombre}`,"error"); return; }
      if (prod.stock < item.qty) { toast(`Stock insuficiente para "${prod.nombre}" — disponible: ${prod.stock}`,"error"); return; }
    }
    setPaying(true);
    setTimeout(()=>{
      const id = `VTA-${String(ventas.length+1).padStart(4,"0")}`;
      const nv = {id,cliente:cliente.trim(),productos:cart.map(i=>({nombre:i.nombre,qty:i.qty,precio:i.precio})),subtotal,igv,total,metodo,fecha:new Date().toLocaleString("es-PE"),estado:"Completada"};
      setVentas(v=>[nv,...v]);
      setProductos(ps=>ps.map(p=>{const ci=cart.find(c=>c.id===p.id);return ci?{...p,stock:p.stock-ci.qty,ventas:p.ventas+ci.qty}:p;}));
      setCart([]);setCliente("");setPaying(false);
      setBoletaVenta(nv);
    },800);
  };

  const filteredProds = productos.filter(p=>{
    const q=search.toLowerCase();
    return p.activo && (!q || p.nombre.toLowerCase().includes(q)||p.marca.toLowerCase().includes(q)) && (!marcaFilter||p.marca===marcaFilter);
  });

  const marcas = [...new Set(productos.map(p=>p.marca))];

  return (
    <div style={{animation:"fadeUp .4s ease"}}>
      <div className="page-header">
        <div><div className="page-title">Módulo de Ventas</div></div>
        <div style={{display:"flex",gap:8}}>
          <button className={`btn ${tab==="pos"?"btn-primary":"btn-secondary"}`} onClick={()=>setTab("pos")}>🖥️ Punto de Venta</button>
          <button className={`btn ${tab==="hist"?"btn-primary":"btn-secondary"}`} onClick={()=>setTab("hist")}>📋 Historial</button>
        </div>
      </div>

      {tab==="pos" && (
        <div className="pos-layout">
          <div className="pos-products">
            <div className="filter-row" style={{marginBottom:12}}>
              <SearchInput value={search} onChange={setSearch} placeholder="Buscar producto..."/>
              <select className="form-input form-select" style={{width:160}} value={marcaFilter} onChange={e=>setMarcaFilter(e.target.value)}>
                <option value="">Todas las marcas</option>
                {marcas.map(m=><option key={m}>{m}</option>)}
              </select>
            </div>
            <div className="pos-product-grid">
              {filteredProds.map(p=>(
                <div key={p.id} className={`pos-prod-card ${p.stock===0?"out":""}`} onClick={()=>addToCart(p)}>
                  <img src={p.img} alt="" className="pos-prod-img" onError={e=>{e.target.style.display="none"}}/>
                  <div className="pos-prod-body">
                    <div className="pos-prod-name">{p.nombre}</div>
                    <div className="pos-prod-price">S/ {p.precio.toLocaleString("es-PE")}</div>
                    <div className="pos-prod-stock">{p.stock===0?"Agotado":`${p.stock} disponibles`}</div>
                  </div>
                </div>
              ))}
            </div>
          </div>

          <div className="pos-cart">
            <div className="pos-cart-header">🛒 Carrito ({cart.reduce((s,i)=>s+i.qty,0)} items)</div>
            <div className="pos-cart-items">
              {cart.length===0 ? <div style={{textAlign:"center",padding:30,color:"var(--text3)"}}>Selecciona productos</div> : cart.map(item=>(
                <div key={item.id} className="pos-cart-item">
                  <div style={{flex:1,minWidth:0}}>
                    <div className="pos-cart-item-name">{item.nombre}</div>
                    <div className="pos-cart-item-price">S/ {(item.precio*item.qty).toLocaleString("es-PE")}</div>
                  </div>
                  <div style={{display:"flex",alignItems:"center",gap:4}}>
                    <button className="pos-qty-btn" onClick={()=>updateQty(item.id,item.qty-1)}>−</button>
                    <span className="pos-qty">{item.qty}</span>
                    <button className="pos-qty-btn" onClick={()=>updateQty(item.id,item.qty+1)}>+</button>
                  </div>
                </div>
              ))}
            </div>
            <div className="pos-cart-footer">
              <div className="form-group" style={{marginBottom:8}}>
                <label className="form-label">DNI del cliente</label>
                <div style={{display:"flex",gap:6}}>
                  <input
                    className="form-input"
                    value={dniCliente}
                    onChange={e=>setDniCliente(e.target.value.replace(/\D/g,"").slice(0,8))}
                    placeholder="00000000"
                    maxLength={8}
                    style={{letterSpacing:"2px",fontFamily:"var(--mono)"}}
                  />
                  <button
                    className="btn btn-primary"
                    style={{whiteSpace:"nowrap",padding:"0 12px",fontSize:12}}
                    onClick={buscarDNIPos}
                    disabled={buscandoDNIPos||dniCliente.length!==8}
                  >
                    {buscandoDNIPos?"⏳":"🔍"}
                  </button>
                </div>
              </div>
              <div className="form-group" style={{marginBottom:10}}>
                <label className="form-label">Nombre del cliente *</label>
                <input className="form-input" value={cliente} onChange={e=>setCliente(e.target.value)} placeholder="O escríbelo manualmente"/>
              </div>
              <div className="form-group" style={{marginBottom:12}}>
                <label className="form-label">Método de Pago</label>
                <select className="form-input form-select" value={metodo} onChange={e=>setMetodo(e.target.value)}>
                  {["Efectivo","Tarjeta","Yape","Plin","Transferencia"].map(m=><option key={m}>{m}</option>)}
                </select>
              </div>
              <div className="pos-total-row"><span>Subtotal</span><span>S/ {subtotal.toFixed(2)}</span></div>
              <div className="pos-total-row"><span>IGV (18%)</span><span>S/ {igv.toFixed(2)}</span></div>
              <div className="pos-total-row"><span>Descuento</span><span style={{color:"var(--green3)"}}>S/ 0.00</span></div>
              <div className="pos-total-row grand"><span>TOTAL</span><span style={{color:"var(--blue)"}}>S/ {total.toFixed(2)}</span></div>
              <button className="btn btn-success pos-btn-pagar" disabled={paying||cart.length===0||!cliente} onClick={procesarVenta}>
                {paying?"⏳ Procesando...":"💳 Completar Venta"}
              </button>
              {cart.length>0&&<button className="btn btn-danger" style={{width:"100%",marginTop:6,fontSize:12}} onClick={()=>setCart([])}>🗑 Vaciar carrito</button>}
            </div>
          </div>
        </div>
      )}

      {tab==="hist" && (
        <div className="card">
          <div className="card-header">
            <span className="card-title">📋 Historial de Ventas</span>
            <span className="badge badge-blue">{ventas.length} ventas</span>
          </div>
          <div className="table-wrap">
            <table>
              <thead><tr><th>ID Venta</th><th>Cliente</th><th>Productos</th><th>Subtotal</th><th>IGV</th><th>Total</th><th>Método</th><th>Fecha</th><th>Estado</th></tr></thead>
              <tbody>
                {ventas.map(v=>(
                  <tr key={v.id}>
                    <td><span style={{fontFamily:"var(--mono)",color:"var(--blue)",fontWeight:700}}>{v.id}</span></td>
                    <td>{v.cliente}</td>
                    <td>{v.productos.map(p=>`${p.nombre}(x${p.qty||p.cantidad})`).join(", ").slice(0,40)}...</td>
                    <td>S/ {v.subtotal.toFixed(2)}</td>
                    <td>S/ {v.igv.toFixed(2)}</td>
                    <td><strong style={{color:"var(--blue)"}}>S/ {v.total.toFixed(2)}</strong></td>
                    <td><span className="tag">{v.metodo}</span></td>
                    <td style={{fontFamily:"var(--mono)",fontSize:11}}>{v.fecha}</td>
                    <td><EstadoBadge estado={v.estado}/></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {boletaVenta && (
        <div className="boleta-overlay" onClick={()=>setBoletaVenta(null)}>
          <div className="boleta-card" onClick={e=>e.stopPropagation()}>
            <div className="boleta-header">
              <div className="boleta-empresa">FinTechmovil S.A.C.</div>
              <div className="boleta-ruc">RUC: 20601234567 · Jr. Tecnología 123, Lima</div>
              <span className="boleta-titulo">📄 Boleta de Venta</span>
            </div>
            <div className="boleta-body">
              <div className="boleta-row">
                <span style={{color:"var(--text3)"}}>N° Venta</span>
                <span style={{fontFamily:"var(--mono)",fontWeight:700,color:"var(--blue)"}}>{boletaVenta.id}</span>
              </div>
              <div className="boleta-row">
                <span style={{color:"var(--text3)"}}>Fecha</span>
                <span style={{fontFamily:"var(--mono)",fontSize:12}}>{boletaVenta.fecha}</span>
              </div>
              <div className="boleta-row">
                <span style={{color:"var(--text3)"}}>Cliente</span>
                <span style={{fontWeight:600}}>{boletaVenta.cliente}</span>
              </div>
              <div className="boleta-row">
                <span style={{color:"var(--text3)"}}>Método de Pago</span>
                <span className="tag">{boletaVenta.metodo}</span>
              </div>
              <div className="boleta-section">Detalle de Productos</div>
              {boletaVenta.productos.map((p,i)=>(
                <div key={i} className="boleta-row">
                  <span style={{maxWidth:"60%"}}>{p.nombre} <span style={{color:"var(--text3)"}}>x{p.qty}</span></span>
                  <span style={{fontWeight:600}}>S/ {(p.precio*p.qty).toFixed(2)}</span>
                </div>
              ))}
              <div className="boleta-row" style={{marginTop:10}}>
                <span style={{color:"var(--text3)"}}>Subtotal</span>
                <span>S/ {boletaVenta.subtotal.toFixed(2)}</span>
              </div>
              <div className="boleta-row">
                <span style={{color:"var(--text3)"}}>IGV (18%)</span>
                <span>S/ {boletaVenta.igv.toFixed(2)}</span>
              </div>
              <div className="boleta-total-row">
                <span>TOTAL A PAGAR</span>
                <span>S/ {boletaVenta.total.toFixed(2)}</span>
              </div>
              <div style={{textAlign:"center",marginTop:16,fontSize:11,color:"var(--text3)"}}>
                ¡Gracias por su compra! · Conserve este comprobante
              </div>
            </div>
            <div className="boleta-footer">
              <button className="btn btn-primary" style={{flex:1}} onClick={()=>window.print()}>🖨️ Imprimir</button>
              <button className="btn btn-secondary" style={{flex:1}} onClick={()=>setBoletaVenta(null)}>✓ Cerrar</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

/* ── REPORTES ─────────────────────────────────────────────────── */
function Reportes({productos}) {
  const totalVentas = VENTAS_MOCK.reduce((s,v)=>s+v.total,0);
  const prodsVendidos = VENTAS_MOCK.reduce((s,v)=>s+v.productos.reduce((ss,p)=>ss+(p.qty||1),0),0);
  const ticketProm = totalVentas / VENTAS_MOCK.length;

  const exportarReporteCompleto = () => {
    const hoy = new Date().toLocaleDateString("es-PE").replace(/\//g,"-");
    // Exportar productos
    exportCSV(
      productos.map(p=>({
        Marca: p.marca, Modelo: p.modelo, Categoria: p.categoria,
        "Precio (S/)": p.precio, Stock: p.stock, "Stock Minimo": p.stockMinimo,
        Ventas: p.ventas, "Ingreso Estimado (S/)": (p.precio*p.ventas).toFixed(2),
        Estado: p.activo ? "Activo" : "Inactivo"
      })),
      `FinTechMovil_Productos_${hoy}.csv`
    );
    // Exportar ventas
    exportCSV(
      VENTAS_MOCK.map(v=>({
        "ID Venta": v.id, Cliente: v.cliente,
        "Subtotal (S/)": v.subtotal.toFixed(2),
        "IGV (S/)": v.igv.toFixed(2),
        "Total (S/)": v.total.toFixed(2),
        "Metodo de Pago": v.metodo, Fecha: v.fecha, Estado: v.estado
      })),
      `FinTechMovil_Ventas_${hoy}.csv`
    );
  };

  return (
    <div style={{animation:"fadeUp .4s ease"}}>
      <div className="page-header">
        <div><div className="page-title">Reportes y Estadísticas</div><div className="page-sub">Análisis del rendimiento del negocio</div></div>
        <div style={{display:"flex",gap:8}}>
          <button className="btn btn-secondary" onClick={exportarReporteCompleto}>📥 Exportar a Excel (CSV)</button>
        </div>
      </div>

      <div className="kpi-grid">
        {[
          {label:"Ingresos Totales",val:`S/ ${totalVentas.toLocaleString("es-PE",{maximumFractionDigits:0})}`,icon:"💰",cls:"kpi-blue"},
          {label:"Ventas Completadas",val:VENTAS_MOCK.length,icon:"🧾",cls:"kpi-green"},
          {label:"Productos Vendidos",val:prodsVendidos,icon:"📦",cls:"kpi-amber"},
          {label:"Ticket Promedio",val:`S/ ${ticketProm.toFixed(0)}`,icon:"📊",cls:"kpi-purple"},
        ].map((k,i)=>(
          <div key={i} className={`kpi-card ${k.cls}`}>
            <div className="kpi-icon-wrap">{k.icon}</div>
            <div><div className="kpi-label">{k.label}</div><div className="kpi-val" style={{fontSize:20}}>{k.val}</div></div>
          </div>
        ))}
      </div>

      <div className="charts-row">
        <div className="card">
          <div className="card-header"><span className="card-title">📈 Ingresos Mensuales (S/.)</span></div>
          <div className="card-body"><LineChart data={VENTAS_MENSUALES}/></div>
        </div>
        <div className="card">
          <div className="card-header"><span className="card-title">📊 Ventas por Mes</span></div>
          <div className="card-body"><BarChart data={VENTAS_MENSUALES}/></div>
        </div>
      </div>

      <div style={{display:"grid",gridTemplateColumns:"1fr 1fr",gap:16}}>
        <div className="card">
          <div className="card-header"><span className="card-title">🏆 Productos Más Rentables</span></div>
          <div className="table-wrap">
            <table>
              <thead><tr><th>Producto</th><th>Precio</th><th>Ventas</th><th>Ingreso Aprox.</th></tr></thead>
              <tbody>
                {[...productos].sort((a,b)=>b.precio*b.ventas-a.precio*a.ventas).slice(0,6).map(p=>(
                  <tr key={p.id}>
                    <td>{p.nombre}</td>
                    <td>S/ {p.precio.toLocaleString("es-PE")}</td>
                    <td><strong>{p.ventas}</strong></td>
                    <td style={{color:"var(--green3)",fontWeight:700}}>S/ {(p.precio*p.ventas*1.18).toLocaleString("es-PE",{maximumFractionDigits:0})}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        <div className="card">
          <div className="card-header"><span className="card-title">💳 Ventas por Método de Pago</span></div>
          <div className="card-body">
            {["Efectivo","Tarjeta","Yape","Plin","Transferencia"].map((m,i) => {
              const count = VENTAS_MOCK.filter(v=>v.metodo===m).length;
              const total = VENTAS_MOCK.filter(v=>v.metodo===m).reduce((s,v)=>s+v.total,0);
              const pct = (VENTAS_MOCK.length===0?0:count/VENTAS_MOCK.length*100).toFixed(0);
              const colors = ["#2563EB","#10B981","#F59E0B","#8B5CF6","#EF4444"];
              return (
                <div key={m} style={{marginBottom:12}}>
                  <div style={{display:"flex",justifyContent:"space-between",fontSize:12,marginBottom:4}}>
                    <span style={{fontWeight:600}}>{m}</span>
                    <span style={{color:"var(--text3)"}}>{count} ventas · S/ {total.toFixed(0)}</span>
                  </div>
                  <div style={{height:8,background:"var(--bg)",borderRadius:4,overflow:"hidden"}}>
                    <div style={{height:"100%",width:`${pct}%`,background:colors[i],borderRadius:4}}/>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </div>
  );
}

/* ── CLIENTES ─────────────────────────────────────────────────── */
function Clientes() {
  const [clientes, setClientes] = useState(CLIENTES_MOCK);
  const [search, setSearch] = useState("");
  const [modalOpen, setModalOpen] = useState(false);
  const [editCli, setEditCli] = useState(null);
  const [form, setForm] = useState({nombre:"",email:"",telefono:"",dni:"",ruc:"",ciudad:"Lima"});
  const [buscandoDNI, setBuscandoDNI] = useState(false);
  const [buscandoRUC, setBuscandoRUC] = useState(false);

  const filtered = clientes.filter(c => !search || c.nombre.toLowerCase().includes(search.toLowerCase()) || c.email.toLowerCase().includes(search.toLowerCase()));

  const openAdd = () => { setEditCli(null); setForm({nombre:"",email:"",telefono:"",dni:"",ruc:"",ciudad:"Lima"}); setModalOpen(true); };
  const openEdit = (c) => { setEditCli(c); setForm({...c}); setModalOpen(true); };

  const buscarDNI = async () => {
    if (form.dni.length !== 8) { toast("El DNI debe tener 8 dígitos","error"); return; }
    setBuscandoDNI(true);
    try {
      const nombre = await consultarDNI(form.dni);
      setForm(f => ({...f, nombre}));
      toast(`✅ ${nombre}`);
    } catch(e) {
      toast(`DNI no encontrado (${e.message})`,"error");
    } finally {
      setBuscandoDNI(false);
    }
  };

  const buscarRUC = async () => {
    if (form.ruc.length !== 11) { toast("El RUC debe tener 11 dígitos","error"); return; }
    setBuscandoRUC(true);
    try {
      const data = await consultarRUC(form.ruc);
      setForm(f => ({...f, nombre: data.nombre || f.nombre, ciudad: data.departamento || data.provincia || f.ciudad}));
      toast(`✅ ${data.nombre}`);
    } catch(e) {
      toast(`RUC no encontrado (${e.message})`,"error");
    } finally {
      setBuscandoRUC(false);
    }
  };

  const save = () => {
    if (!form.nombre || !form.email) { toast("Complete nombre y email","error"); return; }
    if (editCli) {
      setClientes(cs=>cs.map(c=>c.id===editCli.id?{...c,...form}:c));
      toast(`✏️ Cliente ${form.nombre} actualizado`);
    } else {
      setClientes(cs=>[...cs,{...form,id:Date.now(),compras:0,total:0,fechaReg:new Date().toLocaleDateString("es-PE"),activo:true}]);
      toast(`✅ Cliente ${form.nombre} registrado`);
    }
    setModalOpen(false);
  };

  return (
    <div style={{animation:"fadeUp .4s ease"}}>
      <div className="page-header">
        <div><div className="page-title">Gestión de Clientes</div><div className="page-sub">{filtered.length} clientes registrados</div></div>
        <button className="btn btn-primary" onClick={openAdd}>➕ Nuevo Cliente</button>
      </div>

      <div className="kpi-grid" style={{gridTemplateColumns:"repeat(3,1fr)"}}>
        <div className="kpi-card kpi-blue"><div className="kpi-icon-wrap">👥</div><div><div className="kpi-label">Total Clientes</div><div className="kpi-val" style={{fontSize:22}}>{clientes.length}</div></div></div>
        <div className="kpi-card kpi-green"><div className="kpi-icon-wrap">✅</div><div><div className="kpi-label">Clientes Activos</div><div className="kpi-val" style={{fontSize:22}}>{clientes.filter(c=>c.activo).length}</div></div></div>
        <div className="kpi-card kpi-amber"><div className="kpi-icon-wrap">💰</div><div><div className="kpi-label">Ventas Totales</div><div className="kpi-val" style={{fontSize:18}}>S/ {clientes.reduce((s,c)=>s+c.total,0).toLocaleString("es-PE",{maximumFractionDigits:0})}</div></div></div>
      </div>

      <div className="card">
        <div className="card-body" style={{paddingBottom:0}}>
          <div className="filter-row">
            <SearchInput value={search} onChange={setSearch} placeholder="Buscar por nombre o email..."/>
          </div>
        </div>
        <div className="table-wrap">
          <table>
            <thead><tr><th>Cliente</th><th>Email</th><th>Teléfono</th><th>Ciudad</th><th>Compras</th><th>Total Gastado</th><th>Registro</th><th>Estado</th><th>Acciones</th></tr></thead>
            <tbody>
              {filtered.map(c=>(
                <tr key={c.id}>
                  <td>
                    <div style={{display:"flex",alignItems:"center",gap:8}}>
                      <div style={{width:32,height:32,borderRadius:"50%",background:"var(--blue3)",color:"var(--blue)",display:"flex",alignItems:"center",justifyContent:"center",fontSize:13,fontWeight:700,flexShrink:0}}>{c.nombre.charAt(0)}</div>
                      <div>
                        <div style={{fontWeight:600}}>{c.nombre}</div>
                        <div style={{fontSize:10,color:"var(--text3)",fontFamily:"var(--mono)"}}>{c.ruc?`RUC: ${c.ruc}`:`DNI: ${c.dni}`}</div>
                      </div>
                    </div>
                  </td>
                  <td>{c.email}</td>
                  <td>{c.telefono}</td>
                  <td><span className="tag">{c.ciudad}</span></td>
                  <td><strong>{c.compras}</strong></td>
                  <td style={{color:"var(--green3)",fontWeight:700}}>S/ {c.total.toLocaleString("es-PE")}</td>
                  <td style={{fontFamily:"var(--mono)",fontSize:11}}>{c.fechaReg}</td>
                  <td><EstadoBadge estado={c.activo?"Activo":"Inactivo"}/></td>
                  <td>
                    <div style={{display:"flex",gap:4}}>
                      <button className="btn-icon" onClick={()=>openEdit(c)}>✏️</button>
                      <button className="btn-icon" onClick={()=>{setClientes(cs=>cs.map(x=>x.id===c.id?{...x,activo:!x.activo}:x));toast(`${c.activo?"❌":"✅"} ${c.nombre}`)}}>{c.activo?"🔴":"🟢"}</button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {filtered.length===0&&<div className="empty-state"><div className="empty-icon">👥</div><p>Sin clientes</p></div>}
        </div>
      </div>

      {modalOpen && (
        <div className="modal-overlay" onClick={e=>e.target===e.currentTarget&&setModalOpen(false)}>
          <div className="modal" style={{maxWidth:500}}>
            <div className="modal-header">
              <span className="modal-title">{editCli?"✏️ Editar Cliente":"➕ Nuevo Cliente"}</span>
              <button className="modal-close" onClick={()=>setModalOpen(false)}>✕</button>
            </div>
            <div className="modal-body">
              <div className="form-grid">
                <div className="form-group">
                  <label className="form-label">DNI <span style={{color:"var(--text3)",fontWeight:400}}>(8 dígitos)</span></label>
                  <div style={{display:"flex",gap:6}}>
                    <input className="form-input" value={form.dni} onChange={e=>setForm({...form,dni:e.target.value.replace(/\D/g,"").slice(0,8)})} placeholder="00000000" maxLength={8}/>
                    <button className="btn btn-primary" style={{whiteSpace:"nowrap",padding:"0 14px"}} onClick={buscarDNI} disabled={buscandoDNI}>
                      {buscandoDNI?"⏳":"🔍"} {buscandoDNI?"Buscando...":"Buscar"}
                    </button>
                  </div>
                </div>
                <div className="form-group">
                  <label className="form-label">RUC <span style={{color:"var(--text3)",fontWeight:400}}>(11 dígitos)</span></label>
                  <div style={{display:"flex",gap:6}}>
                    <input className="form-input" value={form.ruc} onChange={e=>setForm({...form,ruc:e.target.value.replace(/\D/g,"").slice(0,11)})} placeholder="20000000000" maxLength={11}/>
                    <button className="btn btn-primary" style={{whiteSpace:"nowrap",padding:"0 14px"}} onClick={buscarRUC} disabled={buscandoRUC}>
                      {buscandoRUC?"⏳":"🔍"} {buscandoRUC?"Buscando...":"Buscar"}
                    </button>
                  </div>
                </div>
                <div className="form-group full">
                  <label className="form-label">Nombre / Razón Social *</label>
                  <input className="form-input" value={form.nombre} onChange={e=>setForm({...form,nombre:e.target.value})} placeholder="Se completa automáticamente con DNI/RUC"/>
                </div>
                <div className="form-group"><label className="form-label">Email *</label><input className="form-input" type="email" value={form.email} onChange={e=>setForm({...form,email:e.target.value})} placeholder="juan@email.com"/></div>
                <div className="form-group"><label className="form-label">Teléfono</label><input className="form-input" value={form.telefono} onChange={e=>setForm({...form,telefono:e.target.value})} placeholder="999 000 000"/></div>
                <div className="form-group"><label className="form-label">Ciudad</label>
                  <select className="form-input form-select" value={form.ciudad} onChange={e=>setForm({...form,ciudad:e.target.value})}>
                    {["Lima","Arequipa","Trujillo","Cusco","Piura","Chiclayo","Iquitos","Puno"].map(c=><option key={c}>{c}</option>)}
                  </select>
                </div>
              </div>
            </div>
            <div className="modal-footer">
              <button className="btn btn-secondary" onClick={()=>setModalOpen(false)}>Cancelar</button>
              <button className="btn btn-primary" onClick={save}>{editCli?"💾 Guardar":"➕ Registrar"}</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

/* ── LOGIN ────────────────────────────────────────────────────── */
function Login({onLogin}) {
  const [u,setU]=useState(""); const [p,setP]=useState(""); const [err,setErr]=useState(""); const [loading,setLoading]=useState(false);
  const submit=async e=>{
    e.preventDefault(); setErr(""); setLoading(true);
    try {
      const res=await fetch(`${API}/auth/login`,{
        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify({username:u,password:p})
      });
      if(res.ok){
        const data=await res.json();
        onLogin({nombre:data.nombre||u,email:u,rol:data.role||"ADMIN"},data.token);
      } else {
        setErr("Credenciales incorrectas");
      }
    } catch {
      if((u==="admin"&&p==="admin123")){
        onLogin({nombre:"Administrador",email:u,rol:"ADMIN"},null);
      } else if(u&&p) {
        setErr("No se pudo conectar al servidor");
      } else { setErr("Completa usuario y contraseña"); }
    }
    setLoading(false);
  };
  return (
    <div className="login-bg">
      <div className="login-card">
        <div className="login-header">
          <div className="login-logo">📱</div>
          <div className="login-title">TechMovil</div>
          <div className="login-sub">Sistema de Gestión Empresarial</div>
        </div>
        <form onSubmit={submit}>
          <label style={{fontSize:11,fontWeight:600,color:"var(--text3)",fontFamily:"var(--mono)",display:"block",marginBottom:5}}>USUARIO</label>
          <input className="login-input" placeholder="admin" value={u} onChange={e=>setU(e.target.value)} autoFocus/>
          <label style={{fontSize:11,fontWeight:600,color:"var(--text3)",fontFamily:"var(--mono)",display:"block",marginBottom:5}}>CONTRASEÑA</label>
          <input className="login-input" type="password" placeholder="••••" value={p} onChange={e=>setP(e.target.value)}/>
          {err&&<div style={{color:"var(--red3)",fontSize:12,marginBottom:8,fontFamily:"var(--mono)"}}>⚠️ {err}</div>}
          <button className="login-btn" type="submit" disabled={loading}>{loading?"⏳ Verificando...":"Ingresar al Sistema →"}</button>
        </form>
        <div className="login-hint">usuario: admin · contraseña: admin123</div>
      </div>
    </div>
  );
}

/* ── MAIN APP ─────────────────────────────────────────────────── */
const PAGES = [
  {id:"dashboard",label:"Dashboard",icon:"📊",section:"PRINCIPAL"},
  {id:"productos",label:"Productos",icon:"📱",section:"GESTIÓN"},
  {id:"inventario",label:"Inventario",icon:"🏭",section:"GESTIÓN"},
  {id:"ventas",label:"Ventas / POS",icon:"🛒",section:"GESTIÓN"},
  {id:"reportes",label:"Reportes",icon:"📈",section:"ANÁLISIS"},
  {id:"clientes",label:"Clientes",icon:"👥",section:"ANÁLISIS"},
];

export default function App() {
  const [user, setUser] = useState(null);
  const [page, setPage] = useState("dashboard");
  const [collapsed, setCollapsed] = useState(false);
  const [productos, setProductos] = useState(PRODUCTOS_MOCK);

  useEffect(()=>{
    const s=document.createElement("style"); s.textContent=CSS; document.head.appendChild(s);
    const saved=localStorage.getItem("techmovil_user");
    if(saved) try { setUser(JSON.parse(saved)); } catch{}
    return ()=>document.head.removeChild(s);
  },[]);

  const onLogin=(u,token)=>{ setUser(u); localStorage.setItem("techmovil_user",JSON.stringify(u)); if(token) localStorage.setItem("techmovil_token",token); };
  const onLogout=()=>{ setUser(null); localStorage.removeItem("techmovil_user"); localStorage.removeItem("techmovil_token"); setPage("dashboard"); toast("Sesión cerrada","info"); };

  const alertas = productos.filter(p=>p.stock<=p.stockMinimo).length;

  if (!user) return <><Login onLogin={onLogin}/><ToastProvider/></>;

  const sections = [...new Set(PAGES.map(p=>p.section))];
  const pageTitles = {dashboard:"Dashboard Principal",productos:"Gestión de Productos",inventario:"Control de Inventario",ventas:"Módulo de Ventas",reportes:"Reportes y Análisis",clientes:"Gestión de Clientes"};

  return (
    <>
      <div className="app-shell">
        <div className={`sidebar ${collapsed?"collapsed":""}`}>
          <div className="sidebar-logo" style={{cursor:"pointer"}} onClick={()=>setCollapsed(!collapsed)}>
            <div className="logo-icon">📱</div>
            {!collapsed&&<div><div className="logo-text">TechMovil</div><div className="logo-sub">Sistema ERP v1.0</div></div>}
          </div>
          {sections.map(sec=>(
            <div key={sec}>
              {!collapsed&&<div className="sidebar-section">{sec}</div>}
              {PAGES.filter(p=>p.section===sec).map(pg=>(
                <div key={pg.id} className={`nav-item ${page===pg.id?"active":""}`} onClick={()=>setPage(pg.id)} title={collapsed?pg.label:""}>
                  <span className="nav-icon">{pg.icon}</span>
                  {!collapsed&&<span className="nav-label">{pg.label}</span>}
                  {!collapsed&&pg.id==="inventario"&&alertas>0&&<span className="nav-badge">{alertas}</span>}
                </div>
              ))}
            </div>
          ))}
          <div className="sidebar-footer">
            <div className="user-card" title={collapsed?user.nombre:""}>
              <div className="user-avatar">{user.nombre.charAt(0)}</div>
              {!collapsed&&<div><div className="user-name">{user.nombre}</div><div className="user-role">{user.rol}</div></div>}
            </div>
            {!collapsed&&<div className="nav-item" onClick={onLogout}><span className="nav-icon">🚪</span><span className="nav-label">Cerrar Sesión</span></div>}
          </div>
        </div>

        <div className="main-area">
          <div className="topbar">
            <div className="topbar-title">{pageTitles[page]}</div>
            <div className="topbar-search" onClick={()=>toast("🔍 Búsqueda global","info")}>🔍 Buscar en el sistema...</div>
            <div className="topbar-icon-btn" title="Notificaciones" onClick={()=>toast(`⚠️ ${alertas} alertas de stock bajo`,"info")}>
              🔔{alertas>0&&<div className="notif-dot"/>}
            </div>
            <div className="topbar-icon-btn" title="Configuración" onClick={()=>toast("⚙️ Configuración del sistema","info")}>⚙️</div>
            <div style={{display:"flex",alignItems:"center",gap:8,padding:"6px 10px",borderRadius:8,background:"var(--bg)",border:"1px solid var(--border)",cursor:"pointer"}} onClick={onLogout}>
              <div style={{width:28,height:28,borderRadius:"50%",background:"var(--blue3)",color:"var(--blue)",display:"flex",alignItems:"center",justifyContent:"center",fontSize:12,fontWeight:700}}>{user.nombre.charAt(0)}</div>
              <div style={{fontSize:12}}><div style={{fontWeight:600}}>{user.nombre}</div><div style={{fontSize:10,color:"var(--text3)"}}>{user.rol}</div></div>
            </div>
          </div>

          <div className="page-content">
            {page==="dashboard"&&<Dashboard productos={productos}/>}
            {page==="productos"&&<Productos productos={productos} setProductos={setProductos}/>}
            {page==="inventario"&&<Inventario productos={productos} setProductos={setProductos}/>}
            {page==="ventas"&&<Ventas productos={productos} setProductos={setProductos}/>}
            {page==="reportes"&&<Reportes productos={productos}/>}
            {page==="clientes"&&<Clientes/>}
          </div>
        </div>
      </div>
      <ToastProvider/>
    </>
  );
}
