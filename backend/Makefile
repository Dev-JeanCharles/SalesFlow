# Caminho do docker-compose
COMPOSE_FILE=infra/docker/docker-compose.yml

## ---------- Docker Compose ----------
up:
	docker compose -f $(COMPOSE_FILE) up -d

down:
	docker compose -f $(COMPOSE_FILE) down

down-volumes:
	docker compose -f $(COMPOSE_FILE) down -v

status:
	docker compose -f $(COMPOSE_FILE) ps

# 🔥 Derruba containers, remove volumes e sobe novamente
reset:
	docker compose -f $(COMPOSE_FILE) down -v
	docker compose -f $(COMPOSE_FILE) up -d

## ---------- Ajuda ----------
help:
	@echo ""
	@echo "📦 Docker Compose - Comandos disponíveis"
	@echo ""
	@echo "  make up             -> Sobe os containers"
	@echo "  make down           -> Derruba os containers"
	@echo "  make down-volumes   -> Derruba os containers e remove volumes"
	@echo "  make status         -> Mostra o status dos containers"
	@echo "  make reset          -> Reset completo (down -v + up)"
	@echo ""
