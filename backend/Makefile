# ==============================
# Configurações
# ==============================

# Caminho do docker-compose
COMPOSE_FILE=infra/docker/docker-compose.yml

# LocalStack / AWS
LOCALSTACK_ENDPOINT=http://localhost:4566
AWS_REGION=us-east-1
QUEUE_NAME=sale-activation-queue
QUEUE_URL=$(LOCALSTACK_ENDPOINT)/000000000000/$(QUEUE_NAME)

# ==============================
# Docker Compose
# ==============================

up:
	@docker compose -f $(COMPOSE_FILE) up -d

down:
	@docker compose -f $(COMPOSE_FILE) down

down-volumes:
	@docker compose -f $(COMPOSE_FILE) down -v

status:
	@docker compose -f $(COMPOSE_FILE) ps

reset:
	@docker compose -f $(COMPOSE_FILE) down -v
	@docker compose -f $(COMPOSE_FILE) up -d

# ==============================
# LocalStack / SQS
# ==============================

# Lista todas as filas SQS
queue-list:
	@aws --endpoint-url=$(LOCALSTACK_ENDPOINT) \
	    sqs list-queues \
	    --region $(AWS_REGION)

# Cria a fila de ativação de vendas
queue-create:
	@aws --endpoint-url=$(LOCALSTACK_ENDPOINT) \
	    sqs create-queue \
	    --queue-name $(QUEUE_NAME) \
	    --region $(AWS_REGION)

# Mostra atributos da fila
queue-attrs:
	@aws --endpoint-url=$(LOCALSTACK_ENDPOINT) \
	    sqs get-queue-attributes \
	    --queue-url $(QUEUE_URL) \
	    --attribute-names All \
	    --region $(AWS_REGION)

# Lê mensagens da fila (não remove)
queue-read:
	@aws --endpoint-url=$(LOCALSTACK_ENDPOINT) \
	    sqs receive-message \
	    --queue-url $(QUEUE_URL) \
	    --max-number-of-messages 10 \
	    --wait-time-seconds 1 \
	    --region $(AWS_REGION)

# Lê e remove uma mensagem da fila (debug)
queue-read-delete:
	@aws --endpoint-url=$(LOCALSTACK_ENDPOINT) \
	    sqs receive-message \
	    --queue-url $(QUEUE_URL) \
	    --max-number-of-messages 1 \
	    --wait-time-seconds 1 \
	    --query "Messages[0].ReceiptHandle" \
	    --output text | xargs -I {} aws --endpoint-url=$(LOCALSTACK_ENDPOINT) \
	        sqs delete-message \
	        --queue-url $(QUEUE_URL) \
	        --receipt-handle {} \
	        --region $(AWS_REGION)

# ==============================
# Ajuda
# ==============================

help:
	@echo ""
	@echo "📦 Docker Compose"
	@echo ""
	@echo "  make up               -> Sobe os containers"
	@echo "  make down             -> Derruba os containers"
	@echo "  make down-volumes     -> Derruba containers e volumes"
	@echo "  make status           -> Status dos containers"
	@echo "  make reset            -> Reset completo"
	@echo ""
	@echo "📨 SQS / LocalStack"
	@echo ""
	@echo "  make queue-list       -> Lista filas SQS"
	@echo "  make queue-create     -> Cria a fila sale-activation-queue"
	@echo "  make queue-attrs      -> Mostra atributos da fila"
	@echo "  make queue-read       -> Lê mensagens da fila (sem deletar)"
	@echo "  make queue-read-delete-> Lê e remove uma mensagem (debug)"
	@echo ""
