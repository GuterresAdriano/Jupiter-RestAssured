package br.com.dbserver.jupiter_restAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;


public class TesteApi {
	private static int STATUS_OK = 200;
	
	@BeforeEach
	public void setup() {
		RestAssured.baseURI = "http://5d9cc58566d00400145c9ed4.mockapi.io";
	}

	@Test
	public void verificarSkuItensNocarrinho() {
		String skuAEsperado = "demo_2";
		String skuBEsperado = "demo_1";
		String skuCEsperado = "demo_7";
		
		ResponseSpecBuilder builder = new ResponseSpecBuilder();
		builder.expectStatusCode(200);

		given()
			.log().all()
		.when()
			.get("/shopping_cart")
		.then()
			.statusCode(STATUS_OK)
			.and() 
			.statusCode(STATUS_OK)
			.body("color", hasSize(3))
			.body("sku[0]", is(skuAEsperado))
			.body("sku[1]", is(skuBEsperado))
			.body("sku[2]", is(skuCEsperado));
	}

	@Test
	public void verificarCoresItensNocarrinho() {
		String corAEsperada = "Black";
		String corBEsperada = "Orange";
		String corCEsperada = "Yellow";

		given()
		.when()
			.get("/shopping_cart")
		.then()
			.statusCode(STATUS_OK)
			.body("color", hasSize(3))
			.body("color[0]", is(corAEsperada))
			.body("color[1]", is(corBEsperada))
			.body("color[2]", is(corCEsperada));		
	}

	@Test
	public void verificarTamanhosItensNocarrinho() {
		String tamanhoAEsperado = "S";
		String tamanhoBEsperado = "S";
		String tamanhoCEsperado = "S";

		given()
		.when()
			.get("/shopping_cart")
		.then()
			.statusCode(STATUS_OK)
			.body("size", hasSize(3))
			.body("size[0]", is(tamanhoAEsperado))
			.body("size[1]", is(tamanhoBEsperado))
			.body("size[2]", is(tamanhoCEsperado));		
	}

	@Test
	public void verificarPrecosItensNocarrinho() {
		double precoAEsperado = 27.00;
		double precoBEsperado = 16.51;
		double precoCEsperado = 16.40;
		
		ArrayList<String> lista = given().when().get("/shopping_cart").then().extract().path("price");	 
		
		assertEquals(Double.parseDouble(lista.get(0)), precoAEsperado);
		assertEquals(Double.parseDouble(lista.get(1)), precoBEsperado);
		assertEquals(Double.parseDouble(lista.get(2)), precoCEsperado);				
	}

	@Test
	public void verificarTaxaDeEnvio() {		
		given()
		.when()
			.get("/shopping_cart")
		.then()
			.body("total_shipping as double", is(2.0));	
	}
	
	

	@Test
	public void verificarValorTotal() {
		double totalEsperado = 59.91f;		
		double totalReal = 0.0;		
		ArrayList <String> list = given().when().get("/shopping_cart").then().extract().path("price");
		
		for(String x: list ) {
			totalReal += Double.parseDouble(x);
		}	
		assertEquals(totalEsperado, totalReal, 0.01);		
	}
	
	
	
	@Test
	public void verificarDadosPrimeiroItem() {
		String skuEsperado 	   = "demo_2";
		String corEsperada 	   = "Black";
		String tamanhoEsperado = "S";
		String precoEsperado   = "27.00";

		given()
		.when()
			.get("/shopping_cart")
		.then()
			.statusCode(STATUS_OK)
			.body("sku[0]",   is(skuEsperado))
			.and()
			.body("color[0]", is(corEsperada))
			.and()
			.body("size[0]",  is(tamanhoEsperado))
			.and()
			.body("price[0]", is(precoEsperado));		
	}
	
	@Test
	public void verificarDadosSegundoItem() {
		String skuEsperado     = "demo_1";
		String corEsperada     = "Orange";
		String tamanhoEsperado = "S";
		String precoEsperado   = "16.51";

		given()
		.when()
			.get("/shopping_cart")
		.then()
			.statusCode(STATUS_OK)
			.body("sku[1]",   is(skuEsperado))
			.and()
			.body("color[1]", is(corEsperada))
			.and()
			.body("size[1]",  is(tamanhoEsperado))
			.and()
			.body("price[1]", is(precoEsperado));		
	}
	
	@Test
	public void verificarDadosTerceiroItem() {
		String skuEsperado     = "demo_7";
		String corEsperada     = "Yellow";
		String tamanhoEsperado = "S";
		String precoEsperado   = "16.40";

		given()
		.when()
			.get("/shopping_cart")
		.then()
			.statusCode(STATUS_OK)
			.body("sku[2]",   is(skuEsperado))
			.and()
			.body("color[2]", is(corEsperada))
			.and()
			.body("size[2]",  is(tamanhoEsperado))
			.and()
			.body("price[2]", is(precoEsperado));		
	}
	
	@Test
	public void verificarMaiorPreco() {
		String precoEsperado = "27.00";

		given()
		.when()
			.get("/shopping_cart")
		.then()
			.statusCode(STATUS_OK)
			.body("price.findAll{it != null}.max()", is(precoEsperado));
	}
	
	@Test
	public void verificarMenorPreco() {
		String precoEsperado = "16.40"; 

		given()
		.when()
			.get("/shopping_cart")
		.then()
			.statusCode(STATUS_OK)
			.body("price.findAll{it != null}.min()", is(precoEsperado));
	}

}
