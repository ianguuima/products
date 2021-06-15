# 🛒 Product

API para o cadastro de produtos. A documentação da API
e desse readme foi feita em português para facilitar a leitura.
Todo o resto, foi feito em inglês.

Eu defini também um Continuous Integration utilizando
o GitHub Actions para me auxiliar na parte dos testes.

## 🎯 Considerações do Desafio

#### Id da entidade base
Algumas coisas que me chamaram a atenção foi a utilização do ID como String,
uma das melhores opções seria utilizar o UUID para a geração dos IDs, mas como optei pela
praticidade de desenvolvimento, usei um long com um generator em sequência.

#### Status Codes

A parte de validação de campos, junto com o corpo da resposta mostrando o id poderia
ser simplificada e removida, já que a própria resposta já possui um status code.
Há casos onde isso seria interessante, mas nesse projeto, para o ínicio, eu não vejo
como isso poderia ser útil.

#### Criteria

A criteria e o uso dela foi feito de forma bem explícita, definindo que o Product
deveria ser o model principal. Nesse caso, com a introdução de novos models, essa classe
poderia passar a ser genérica para comportar tipos diferentes.

## 🤔 Overall do Projeto e Considerações

O projeto foi majoritariamente feito para ser simples. De implementação simples e execução simples.
Para afirmar essa frase, optei pelo banco em memória H2 para subir a aplicação ao invés de definir
um banco de dados SQL ou NOSQL.

### ⏫ Desempenho da Aplicação

Não houve a preocupação em desempenho dentro da aplicação, mas, caso isso virasse realidade de uma noite
para o dia, poderiamos concentrar nossos esforços em remover as utilizações frequentes da Streams API, visto
que elas trazem problemas de desempenho.

Outro problema de desempenho para essa aplicação, é que o método de pesquisa é feito buscando
todas as entidades e não passando uma query SQL para isso. Isso poderia ser mudado para aumentar o desempenho.

Outro ponto interessante, é que o SpringBoot, utiliza o servidor TomCat
como configuração padrão, e o servidor, por sua vez, trabalha de forma síncrona e bloqueante.
Para resolver o problema, poderiamos optar por uma solução reativa utilizando o WebFlux junto
com o servidor Netty.

### ⏩ Escalabilidade da Aplicação

Para a escalabilidade da aplicação, poderiamos trabalhar com o Docker, o que não foi feito
aqui dentro desse projeto pois foi prezado pela simplicidade de entrega. O Docker
nos possibilitaria criar máquinas para essa API de forma rápida e prática. 
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