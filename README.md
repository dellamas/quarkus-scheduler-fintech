# quarkus-scheduler-fintech

Esse projeto mostra na prática como usar o **Quarkus Scheduler** num contexto de cobrança financeira. A ideia é simples: uma aplicação que mantém uma lista de cobranças e usa jobs agendados para verificar vencimentos, disparar processamentos diários e gerar relatórios — tudo isso sem precisar de um servidor externo de agendamento.

## O que faz

- A cada **30 segundos**, verifica quais cobranças passaram da data de vencimento e atualiza o status automaticamente
- Todo dia às **8h**, processa as cobranças pendentes (simula o envio para a operadora)
- Todo dia às **18h**, gera um relatório com o total vencido e quantidade de cobranças em aberto
- Expõe endpoints REST para consultar o estado das cobranças em tempo real

## Pré-requisitos

- Java 21+
- Maven 3.9+

## Como rodar

```bash
./mvnw quarkus:dev
```

Depois é só testar os endpoints:

```bash
# todas as cobranças
curl http://localhost:8080/cobrancas

# só as vencidas
curl http://localhost:8080/cobrancas/vencidas

# pendentes
curl http://localhost:8080/cobrancas/pendentes

# resumo financeiro
curl http://localhost:8080/cobrancas/resumo
```

## Build para produção

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

---

Se esse projeto te ajudou, deixa uma ⭐ no repositório!

Me acompanha nas redes:
- 💼 [LinkedIn](https://www.linkedin.com/in/luisfabriciodellamas/)
- 🐙 [GitHub](https://github.com/dellamas)
- ✍️ [dev.to](https://dev.to/dellamas)
