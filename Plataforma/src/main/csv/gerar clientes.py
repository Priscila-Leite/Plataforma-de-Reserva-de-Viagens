import csv
import random

# Define the number of rows
num_rows = 20000

# Define lists of possible values for each column
clientes = list(range(num_rows))
cidades = ['RIO', 'SAO', 'PMW', 'REC', 'SP', 'BH', 'FLN', 'BSB', 'MCZ', 'SSA', 'POA']
estadias = list(range(1, 21))  # Estadia de 1 a 20 dias
estrelas = [1, 2, 3, 4, 5]  # Número de estrelas do hotel
orcamentos = list(range(2000, 10001))  # Orçamento de 2000 a 10000 reais

# Open a CSV file to write
with open('Plataforma/src/main/csv/clientes_20000.csv', mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(['Cliente', 'Saída', 'Chegada', 'Estadia (em dias)', 'Estrelas', 'Orçamento (em reais)'])
    
    # Generate and write each row
    for i in range(num_rows):
        cliente = clientes[i]
        saida = random.choice(cidades)
        chegada = random.choice(cidades)
        while chegada == saida:  # Garante que a cidade de chegada seja diferente da cidade de saída
            chegada = random.choice(cidades)
        estadia = random.choice(estadias)
        estrelas_escolhidas = random.choice(estrelas)
        orcamento = random.choice(orcamentos)
        
        writer.writerow([cliente, saida, chegada, estadia, estrelas_escolhidas, orcamento])
    
print(f"Arquivo 'dados.csv' gerado com sucesso com {num_rows} linhas.")
