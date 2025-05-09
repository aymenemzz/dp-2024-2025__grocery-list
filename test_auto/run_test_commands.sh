#!/bin/bash

run_test() {
  echo "----- $1 -----"
  output=$($2 2>&1)
  if [ $? -eq 0 ]; then
    echo "✅ test valide check"
  else
    echo "❌ erreur détectée lors de : $1"
    echo "$output"
  fi
  echo
}

run_test "Ajout JSON default" \
  "java -jar ../target/dp-2024-2025__grocery-list-1.0-SNAPSHOT.jar -s groceries.json add 'Milk' 10"

run_test "Ajout JSON test_auto" \
  "java -jar ../target/dp-2024-2025__grocery-list-1.0-SNAPSHOT.jar -s groceries.json -c 'test_auto' add 'Milk' 10"

run_test "Ajout CSV default" \
  "java -jar ../target/dp-2024-2025__grocery-list-1.0-SNAPSHOT.jar -s groceries.csv -f csv add 'Milk' 10"

run_test "Ajout CSV test_auto" \
  "java -jar ../target/dp-2024-2025__grocery-list-1.0-SNAPSHOT.jar -s groceries.csv -f csv -c 'test_auto' add 'Milk' 10"

run_test "Liste JSON" \
  "java -jar ../target/dp-2024-2025__grocery-list-1.0-SNAPSHOT.jar -s groceries.json list"

run_test "Liste CSV" \
  "java -jar ../target/dp-2024-2025__grocery-list-1.0-SNAPSHOT.jar -s groceries.csv -f csv list"

run_test "Suppression JSON" \
  "java -jar ../target/dp-2024-2025__grocery-list-1.0-SNAPSHOT.jar -s groceries.json remove 'Milk'"

run_test "Suppression CSV" \
  "java -jar ../target/dp-2024-2025__grocery-list-1.0-SNAPSHOT.jar -s groceries.csv -f csv remove 'Milk'"



echo "Suppression des fichiers de test..."
rm -f groceries.csv groceries.json
echo "✅ Nettoyage terminé"