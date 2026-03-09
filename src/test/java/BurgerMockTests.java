import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerMockTests {
    private Burger burger;
    @Mock
    private Bun mockBun;
    @Mock
    private Ingredient mockIngredient1;
    @Mock
    private Ingredient mockIngredient2;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        burger = new Burger();
        mockBun = new Bun("Булка", 10f);
        mockIngredient1 = new Ingredient(IngredientType.FILLING, "Котлета", 5.0f);
        mockIngredient2 = new Ingredient(IngredientType.SAUCE, "Начинка", 75f);
    }
    @Test
    public void setBunsTest(){

        burger.setBuns(mockBun);

        assertEquals(10.0f,mockBun.getPrice(),0.01f);
    }
    @Test
    public void addIngredientTest(){

        burger.addIngredient(mockIngredient1);

        assertEquals(1,burger.ingredients.size());
        assertEquals(mockIngredient1,burger.ingredients.get(0));
        assertEquals("Котлета", burger.ingredients.get(0).getName());
        assertEquals(5.0f, burger.ingredients.get(0).getPrice(), 0.01f);
    }
    @Test
    public void removeIngredientTest(){

        burger.addIngredient(mockIngredient1);
        burger.removeIngredient(0);

        assertEquals(0,burger.ingredients.size());
    }
    @Test
    public void moveIngredientsTest(){

        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        burger.moveIngredient(0,1);

        assertEquals(2,burger.ingredients.size());
        assertEquals(mockIngredient1,burger.ingredients.get(1));
        assertEquals(mockIngredient2,burger.ingredients.get(0));
    }
    @Test
    public void testGetReceiptWithMultipleIngredients() {

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        String receipt = burger.getReceipt();

        String expectedReceipt = String.format("(==== %s ====)%n", mockBun.getName()) +
                String.format("= %s %s =%n", mockIngredient1.getType().toString().toLowerCase(),
                        mockIngredient1.getName()) +
                String.format("= %s %s =%n", mockIngredient2.getType().toString().toLowerCase(),
                        mockIngredient2.getName()) +
                String.format("(==== %s ====)%n", mockBun.getName()) +
                String.format("%nPrice: %f%n", burger.getPrice());

        assertEquals(expectedReceipt, receipt);
    }
}
