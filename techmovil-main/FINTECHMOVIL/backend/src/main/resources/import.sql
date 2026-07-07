-- =========================================================================
-- 0. SELECCIONAR LA BASE DE DATOS
-- =========================================================================
USE techmovil_db;

-- =========================================================================
-- 1. LIMPIEZA DE TABLAS (Para evitar errores de duplicados al reiniciar)
-- =========================================================================
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE ventas;
TRUNCATE TABLE productos;
TRUNCATE TABLE usuarios;
SET FOREIGN_KEY_CHECKS = 1;

-- =========================================================================
-- 2. INSERCIÓN DE PRODUCTOS CON MUESTRA DE FOTOS Y STOCK MÍNIMO
-- =========================================================================
INSERT INTO productos (id, marca, modelo, precio, stock, stock_minimo, imagen_url)
VALUES (1, 'Samsung', 'Galaxy S24', 3500.00, 10, 3, 'https://images.samsung.com/is/image/samsung/p6pim/pe/2401/gallery/pe-galaxy-s24-s928-sm-s928bztqpeo-thumb-539423691');

INSERT INTO productos (id, marca, modelo, precio, stock, stock_minimo, imagen_url)
VALUES (2, 'Apple', 'iPhone 15 Pro Max', 4999.00, 2, 3, 'https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/iphone-15-pro-model-unselect-gallery-2-202309_GEO_EMEA');

INSERT INTO productos (id, marca, modelo, precio, stock, stock_minimo, imagen_url)
VALUES (3, 'Xiaomi', 'Redmi Note 13 Pro Plus', 1850.00, 20, 5, 'https://i01.appmifile.com/v1/MI_1645A5D281E16A8E3EA7E1E7138A1DBC2X/pms_1695103456.40947361.png');

-- =========================================================================
-- 3. USUARIO ADMINISTRADOR (registro de negocio para el modulo de Usuarios;
--    el login de /api/auth/login usa app.admin.username/password, no esta fila)
-- =========================================================================
INSERT INTO usuarios (id_usuario, nombre, username, password, rol, activo)
VALUES (1, 'Administrador', 'admin', 'admin123', 'ADMIN', true);

-- =========================================================================
-- 4. INYECCIÓN DE HISTORIAL DE VENTAS DIRECTO A LA TABLA TRANSACCIONAL
-- =========================================================================

-- Venta en Marzo: 1 Galaxy S24
INSERT INTO ventas (id, cantidad, producto_id)
VALUES (10, 1, 1);

-- Venta en Abril: 1 iPhone 15 Pro Max
INSERT INTO ventas (id, cantidad, producto_id)
VALUES (11, 1, 2);

-- Venta de la semana anterior: 1 Redmi Note 13
INSERT INTO ventas (id, cantidad, producto_id)
VALUES (12, 1, 3);

-- Venta de Ayer: 1 Galaxy S24
INSERT INTO ventas (id, cantidad, producto_id)
VALUES (13, 1, 1);

-- Venta de Hoy: 1 iPhone 15 Pro Max
INSERT INTO ventas (id, cantidad, producto_id)
VALUES (14, 1, 2);