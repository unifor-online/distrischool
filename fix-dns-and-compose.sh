#!/bin/bash

# Script para configurar DNS e rodar Docker Compose
# Execute com: sudo ./fix-dns-and-compose.sh

set -e

echo "🔧 Configurando DNS no WSL..."

# Configurar DNS
echo "nameserver 8.8.8.8" > /etc/resolv.conf
echo "nameserver 8.8.4.4" >> /etc/resolv.conf
echo "nameserver 1.1.1.1" >> /etc/resolv.conf

echo "✅ DNS configurado"


# Reiniciar Docker
echo "🐳 Reiniciando Docker..."
systemctl restart docker || true

sleep 3

# Rodar Docker Compose
echo "🚀 Executando Docker Compose..."
cd /home/amora/distrischool/infra/docker
docker-compose up -d --build

echo ""
echo "✅ Docker Compose executado com sucesso!"
echo ""
echo "📊 Verificando status:"
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
