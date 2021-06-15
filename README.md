# 🛒 Product

API para o cadastro de produtos. A documentação da API
e desse readme foi feita em português para facilitar a leitura.
Todo o resto, foi feito em inglês.

Eu defini também um Continuous Integration utilizando
o GitHub Actions para me auxiliar na parte dos testes.

## 🎯 Considerações do Desafio

#### Status Codes

A parte de validação de campos, junto com o corpo da resposta mostrando o id poderia
ser simplificada e removida, já que a própria resposta já possui um status code.
Há casos onde isso seria interessante, mas nesse projeto, para o ínicio, eu não vejo
como isso poderia ser útil.

#### Criteria

A criteria e o uso dela foi feito de forma bem explícita, definindo que o Product
deveria ser o model principal. Caso houvesse a introdução de novos models, essa classe
poderia passar a ser genérica para comportar tipos diferentes.

## 🤔 Overall do Projeto e Considerações

O projeto foi majoritariamente feito para ser simples. De implementação simples e execução simples.
Por isso, optei pelo banco em memória H2 para subir a aplicação ao invés de definir
um banco de dados SQL ou NOSQL.

### ⏫ Desempenho da Aplicação

Não houve a preocupação em desempenho dentro da aplicação, mas, caso isso virasse realidade, poderiamos concentrar nossos esforços em remover as utilizações frequentes da Streams API, visto que elas trazem problemas de desempenho.

O método de pesquisa por meio das queries é feito buscando após filtrar todas as entidades. Uma classe poderia ser criada para tratar isso de forma inteligente ou utilizar algum buscador como o Sonic ou o Algolia.

Outro ponto interessante, é que o SpringBoot utiliza o servidor TomCat
como configuração padrão. E o servidor, por sua vez, trabalha de forma síncrona e bloqueante.
Para resolver o problema de multiplas requisições, poderiamos optar por uma solução reativa utilizando o WebFlux junto
com o servidor Netty.

### ⏩ Escalabilidade da Aplicação

Para a escalabilidade da aplicação, poderiamos trabalhar com o Docker. Ele
nos possibilitaria containerizar a aplicação tornando a instalação em novos ambientes prática e rápida. 
Caso houvesse a necessidade de múltiplos ambientes e alta disponibilidade, poderiamos
utilizar um orquestrador de containers como o Kubernetes para melhorar ainda mais a experiência.

## ✅ Executando a Aplicação

1. Clone localmente esse repositório pelo comando git clone https://github.com/ianguuima/products.git
2. Abra com o melhor editor existente (IntelliJ, é claro)
3. Execute a aplicação.

Nenhuma configuração adicional é requerida.

## 📰 Documentação

A API foi feita utilizando o Swagger V2. Abaixo, algumas prints.

![img1](https://i.imgur.com/falPIWb.png)
![img2](https://i.imgur.com/00zJBMS.png)
