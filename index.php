<?php

function getCon() {
  try {
    $dir = "pgsql:dbname=BDShopShop"
    .";host=134.209.117.84"
    .";port=3254"
    .";";

    $pdo = new PDO(
      $dir,
      'SSI',
      'SSI*2019'
    );

    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo "Nos conectamos a PUERTO 3254 servidor: 134.209.117.84";
  } catch (\Exception $e) {
    echo "Oh no: PUERTO 3254 servidor: 134.209.117.84 ".$e;
  }
}

getCon();

echo "<hr>";

function getCon2() {
  try {
    $dir = "pgsql:dbname=BDFLL"
    .";host=34.70.2.34"
    .";port=5432"
    .";";

    $pdo = new PDO(
      $dir,
      'postgres',
      'fll2019'
    );

    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo "Nos conectamos a PUERTO 5432 servidor: 34.70.2.34";
  } catch (\Exception $e) {
    echo "Oh no:  PUERTO 5432 servidor: 34.70.2.34 ".$e;
  }
}

getCon2();
