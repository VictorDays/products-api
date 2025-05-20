package test.java.com.spring.apirest;

import com.spring.apirest.controllers.ProductController;
import com.spring.apirest.dtos.product.ProductDTO;
import com.spring.apirest.dtos.product.ProductResponseDTO;
import com.spring.apirest.services.ProductService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDTO productDTO;
    private ProductResponseDTO productResponseDTO;
    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        
        // Como ProductDTO é um record, criamos uma nova instância
        productDTO = new ProductDTO("Test Product", BigDecimal.valueOf(99.99));
        
        // ProductResponseDTO também parece ser um record ou classe imutável
        // Você precisará ajustar conforme a implementação real da sua classe
        // Esta é uma suposição - ajuste conforme necessário
        ProductResponseDTO dto = new ProductResponseDTO("Produto A", new BigDecimal("99.90"), null);

    }

    @Test
// Cenário: Deve salvar um produto e retornar status 201 (CREATED) com o produto salvo
void saveProduct_ShouldReturnCreatedStatusAndProductResponse() {
    when(productService.saveProduct(any(ProductDTO.class))).thenReturn(productResponseDTO);
    
    ResponseEntity<ProductResponseDTO> response = productController.save(productDTO);
    
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(productResponseDTO, response.getBody());
    verify(productService, times(1)).saveProduct(any(ProductDTO.class));
}

@Test
// Cenário: Deve retornar todos os produtos com status 200 (OK)
void getAllProducts_ShouldReturnOkStatusAndProductList() {
    List<ProductResponseDTO> productList = Arrays.asList(productResponseDTO);
    when(productService.findAll()).thenReturn(productList);
    
    ResponseEntity<List<ProductResponseDTO>> response = productController.getAll();
    
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, response.getBody().size());
    assertEquals(productResponseDTO, response.getBody().get(0));
    verify(productService, times(1)).findAll();
}

@Test
// Cenário: Deve retornar um produto pelo ID com status 200 (OK)
void getProductById_ShouldReturnOkStatusAndProduct() {
    when(productService.findProduct(productId)).thenReturn(productResponseDTO);
    
    ResponseEntity<ProductResponseDTO> response = productController.getProduct(productId);
    
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(productResponseDTO, response.getBody());
    verify(productService, times(1)).findProduct(productId);
}

@Test
// Cenário: Deve atualizar um produto e retornar status 204 (NO_CONTENT)
void updateProduct_ShouldReturnNoContentStatusAndUpdatedProduct() {
    when(productService.update(eq(productId), any(ProductDTO.class))).thenReturn(productResponseDTO);
    
    ResponseEntity<ProductResponseDTO> response = productController.update(productDTO, productId);
    
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertEquals(productResponseDTO, response.getBody());
    verify(productService, times(1)).update(eq(productId), any(ProductDTO.class));
}

@Test
// Cenário: Deve excluir um produto com sucesso e retornar status 204 (NO_CONTENT)
void deleteProduct_ShouldReturnNoContentStatus() {
    doNothing().when(productService).delete(productId);
    
    ResponseEntity<String> response = productController.delete(productId);
    
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(productService, times(1)).delete(productId);
}

@Test
// Cenário: Deve retornar status 404 (NOT_FOUND) ao tentar excluir um produto inexistente
void deleteProduct_WhenProductNotFound_ShouldReturnNotFoundStatus() {
    doThrow(ConstraintViolationException.class).when(productService).delete(productId);
    
    ResponseEntity<String> response = productController.delete(productId);
    
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    verify(productService, times(1)).delete(productId);
}
@Test
// Cenário: Deve retornar status 400 (BAD_REQUEST) ao tentar salvar um produto com dados inválidos
void saveProduct_WithInvalidData_ShouldReturnBadRequest() {
    // Simulando exceção de validação
    when(productService.saveProduct(any(ProductDTO.class)))
        .thenThrow(new ConstraintViolationException("Invalid data", null));

    ResponseEntity<ProductResponseDTO> response = productController.save(productDTO);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    verify(productService, times(1)).saveProduct(any(ProductDTO.class));
}
@Test
// Cenário: Deve retornar status 404 (NOT_FOUND) ao buscar produto inexistente
void getProductById_WhenProductNotFound_ShouldReturnNotFound() {
    when(productService.findProduct(productId))
        .thenThrow(new NoSuchElementException("Product not found"));

    ResponseEntity<ProductResponseDTO> response = productController.getProduct(productId);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    verify(productService, times(1)).findProduct(productId);
}
@Test
// Cenário: Deve retornar status 404 (NOT_FOUND) ao tentar atualizar produto inexistente
void updateProduct_WhenProductNotFound_ShouldReturnNotFound() {
    when(productService.update(eq(productId), any(ProductDTO.class)))
        .thenThrow(new NoSuchElementException("Product not found"));

    ResponseEntity<ProductResponseDTO> response = productController.update(productDTO, productId);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    verify(productService, times(1)).update(eq(productId), any(ProductDTO.class));
}
@Test
// Cenário: Deve retornar status 400 (BAD_REQUEST) ao tentar atualizar produto com dados inválidos
void updateProduct_WithInvalidData_ShouldReturnBadRequest() {
    when(productService.update(eq(productId), any(ProductDTO.class)))
        .thenThrow(new ConstraintViolationException("Invalid data", null));

    ResponseEntity<ProductResponseDTO> response = productController.update(productDTO, productId);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    verify(productService, times(1)).update(eq(productId), any(ProductDTO.class));
}
@Test
// Cenário: Deve retornar status 200 (OK) e lista vazia quando não houver produtos
void getAllProducts_WhenNoProductsExist_ShouldReturnEmptyList() {
    when(productService.findAll()).thenReturn(Collections.emptyList());

    ResponseEntity<List<ProductResponseDTO>> response = productController.getAll();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().isEmpty());
    verify(productService, times(1)).findAll();
}

}