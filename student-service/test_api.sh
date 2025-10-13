#!/bin/bash

# Script de Testes Automatizados - Student Service API
# Autor: Copilot
# Data: 13/10/2025

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

BASE_URL="http://localhost:8080"
PASSED=0
FAILED=0

# Função para imprimir com cor
print_color() {
    color=$1
    message=$2
    echo -e "${color}${message}${NC}"
}

# Função para testar endpoint
test_endpoint() {
    test_name=$1
    method=$2
    endpoint=$3
    data=$4
    expected_status=$5
    
    print_color "$BLUE" "\n=== Teste: $test_name ==="
    
    if [ -z "$data" ]; then
        response=$(curl -s -w "\n%{http_code}" -X $method "$BASE_URL$endpoint")
    else
        response=$(curl -s -w "\n%{http_code}" -X $method "$BASE_URL$endpoint" \
            -H "Content-Type: application/json" \
            -d "$data")
    fi
    
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | sed '$d')
    
    if [ "$http_code" == "$expected_status" ]; then
        print_color "$GREEN" "✓ PASSOU - Status: $http_code"
        PASSED=$((PASSED + 1))
    else
        print_color "$RED" "✗ FALHOU - Esperado: $expected_status, Recebido: $http_code"
        FAILED=$((FAILED + 1))
    fi
    
    echo "Resposta: $body" | jq . 2>/dev/null || echo "Resposta: $body"
    
    echo "$body"
}

# Banner
print_color "$YELLOW" "╔════════════════════════════════════════════════╗"
print_color "$YELLOW" "║   STUDENT SERVICE API - TESTES AUTOMATIZADOS  ║"
print_color "$YELLOW" "╚════════════════════════════════════════════════╝"

# Verificar dependências
if ! command -v jq &> /dev/null; then
    print_color "$YELLOW" "⚠ Aviso: jq não encontrado. Instale para melhor formatação: sudo apt-get install jq"
fi

# Teste 1: Health Check
print_color "$BLUE" "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
print_color "$BLUE" "BLOCO 1: HEALTH CHECK"
print_color "$BLUE" "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

test_endpoint "Health Check" "GET" "/actuator/health" "" "200"

# Teste 2: Criar Aluno
print_color "$BLUE" "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
print_color "$BLUE" "BLOCO 2: CRIAR ALUNOS"
print_color "$BLUE" "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

aluno1_data='{
  "nome": "João Victor Amora",
  "dataNascimento": "2000-01-15",
  "endereco": "Rua das Flores, 123",
  "contato": "85999999999",
  "matricula": "2024001",
  "turma": "3A",
  "historicoAcademico": "Cursou Matemática e Português com bom desempenho"
}'

aluno1_response=$(test_endpoint "Criar Aluno 1 - João Victor" "POST" "/api/alunos" "$aluno1_data" "200")
aluno1_id=$(echo "$aluno1_response" | jq -r '.id' 2>/dev/null)

aluno2_data='{
  "nome": "Maria Silva Santos",
  "dataNascimento": "1999-05-20",
  "endereco": "Av. Principal, 789",
  "contato": "85987654321",
  "matricula": "2024002",
  "turma": "3A",
  "historicoAcademico": "Excelente desempenho em Ciências"
}'

aluno2_response=$(test_endpoint "Criar Aluno 2 - Maria Silva" "POST" "/api/alunos" "$aluno2_data" "200")
aluno2_id=$(echo "$aluno2_response" | jq -r '.id' 2>/dev/null)

aluno3_data='{
  "nome": "Pedro Henrique Costa",
  "dataNascimento": "2001-03-10",
  "endereco": "Rua Nova, 456",
  "contato": "85988887777",
  "matricula": "2024003",
  "turma": "2B",
  "historicoAcademico": "Bom aluno, participativo nas aulas"
}'

aluno3_response=$(test_endpoint "Criar Aluno 3 - Pedro Henrique" "POST" "/api/alunos" "$aluno3_data" "200")
aluno3_id=$(echo "$aluno3_response" | jq -r '.id' 2>/dev/null)

print_color "$GREEN" "\n✓ Alunos criados - IDs: $aluno1_id, $aluno2_id, $aluno3_id"

# Teste 3: Buscar Alunos
print_color "$BLUE" "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
print_color "$BLUE" "BLOCO 3: BUSCAR ALUNOS"
print_color "$BLUE" "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

if [ ! -z "$aluno1_id" ]; then
    test_endpoint "Buscar por ID - Aluno 1" "GET" "/api/alunos/$aluno1_id" "" "200"
fi

test_endpoint "Buscar por Matrícula - 2024001" "GET" "/api/alunos/matricula/2024001" "" "200"

test_endpoint "Buscar por Nome - João" "GET" "/api/alunos/nome/João" "" "200"

test_endpoint "Buscar por Nome - Maria" "GET" "/api/alunos/nome/Maria" "" "200"

test_endpoint "Buscar por Turma - 3A" "GET" "/api/alunos/turma/3A" "" "200"

test_endpoint "Buscar por Turma - 2B" "GET" "/api/alunos/turma/2B" "" "200"

# Teste 4: Buscar Aluno Inexistente
print_color "$BLUE" "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
print_color "$BLUE" "BLOCO 4: TESTES DE ERRO"
print_color "$BLUE" "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

test_endpoint "Buscar ID Inexistente" "GET" "/api/alunos/99999" "" "404"

test_endpoint "Buscar Matrícula Inexistente" "GET" "/api/alunos/matricula/9999999" "" "404"

# Teste 5: Editar Aluno
print_color "$BLUE" "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
print_color "$BLUE" "BLOCO 5: EDITAR ALUNOS"
print_color "$BLUE" "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

if [ ! -z "$aluno1_id" ]; then
    aluno1_edit='{
      "nome": "João Victor Amora - ATUALIZADO",
      "dataNascimento": "2000-01-15",
      "endereco": "Rua Atualizada, 999",
      "contato": "85999999999",
      "matricula": "2024001",
      "turma": "3B",
      "historicoAcademico": "Histórico atualizado com novas informações acadêmicas"
    }'
    
    test_endpoint "Editar Aluno 1" "PUT" "/api/alunos/$aluno1_id" "$aluno1_edit" "200"
fi

# Teste 6: Excluir Alunos
print_color "$BLUE" "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
print_color "$BLUE" "BLOCO 6: EXCLUIR ALUNOS"
print_color "$BLUE" "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

if [ ! -z "$aluno3_id" ]; then
    test_endpoint "Excluir Aluno 3" "DELETE" "/api/alunos/$aluno3_id" "" "204"
    
    test_endpoint "Verificar exclusão - Buscar aluno excluído" "GET" "/api/alunos/$aluno3_id" "" "404"
fi

# Resultado Final
print_color "$BLUE" "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
print_color "$BLUE" "RESULTADO FINAL"
print_color "$BLUE" "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

TOTAL=$((PASSED + FAILED))
SUCCESS_RATE=$(echo "scale=2; $PASSED * 100 / $TOTAL" | bc)

print_color "$GREEN" "✓ Testes Passou: $PASSED"
print_color "$RED" "✗ Testes Falhou: $FAILED"
print_color "$YELLOW" "Total de Testes: $TOTAL"
print_color "$YELLOW" "Taxa de Sucesso: ${SUCCESS_RATE}%"

if [ $FAILED -eq 0 ]; then
    print_color "$GREEN" "\n🎉 TODOS OS TESTES PASSARAM! 🎉"
    exit 0
else
    print_color "$RED" "\n⚠️  ALGUNS TESTES FALHARAM ⚠️"
    exit 1
fi
