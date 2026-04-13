import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class BurgerParamTest {
    private Burger burger;
    @Mock
    private Bun mockBun;
    @Mock
    private Ingredient mockIngredient1;
    @Mock
    private Ingredient mockIngredient2;


    private float expectedPrice;
    private float bunPrice;
    private float ingredient1Price;
    private float ingredient2Price;
    private int ingredientCount;

    public BurgerParamTest(float expectedPrice, float bunPrice, float ingredient1Price, float ingredient2Price, int ingredientCount) {
        this.expectedPrice = expectedPrice;
        this.bunPrice = bunPrice;
        this.ingredient1Price = ingredient1Price;
        this.ingredient2Price = ingredient2Price;
        this.ingredientCount = ingredientCount;
    }

@Parameterized.Parameters
public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
            { 325.0f, 100.0f, 50.0f, 75.0f, 2 },  // 100*2 + 50 + 75 = 325
            { 125.0f, 50.0f, 25.0f, 0.0f, 1 },   // 50*2 + 25 = 125
            { 50.0f, 25.0f, 0.0f, 0.0f, 0 },    // 25*2 = 50
            { 450.0f, 150.0f, 100.0f, 50.0f, 2 }, // 150*2 + 100 + 50 = 450
            { 150.0f, 75.0f, 0.0f, 0.0f, 0 },    // 75*2 = 150
            { 325.0f, 125.0f, 50.0f, 25.0f, 2 }   // 125*2 + 50 + 25 = 325
    });
}
@Before
public void setUp() {
        MockitoAnnotations.initMocks(this);
        burger = new Burger();
        burger.setBuns(mockBun);
    }

    @Test
    public void testGetPrice() {
        when(mockBun.getPrice()).thenReturn(bunPrice);

        if (ingredientCount >= 1) {
            when(mockIngredient1.getPrice()).thenReturn(ingredient1Price);
            burger.addIngredient(mockIngredient1);
        }

        if (ingredientCount >= 2) {
            when(mockIngredient2.getPrice()).thenReturn(ingredient2Price);
            burger.addIngredient(mockIngredient2);
        }

        float actualPrice = burger.getPrice();

        assertEquals(expectedPrice, actualPrice, 0.01f);
    }
    @Test
    public void testGetPriceWithNoIngredients() {
        when(mockBun.getPrice()).thenReturn(100.0f);
        burger.setBuns(mockBun);
        burger.ingredients.clear(); // Убедимся, что нет ингредиентов

        float actualPrice = burger.getPrice();

        assertEquals(200.0f, actualPrice, 0.01f); // 100 * 2 = 200
    }

    @Test
    public void testGetPriceWithOneIngredient() {
        when(mockBun.getPrice()).thenReturn(50.0f);
        when(mockIngredient1.getPrice()).thenReturn(25.0f);
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);

        float actualPrice = burger.getPrice();

        assertEquals(125.0f, actualPrice, 0.01f); // 50 * 2 + 25 = 125
    }

    @Test
    public void testGetPriceWithMultipleIngredients() {
        when(mockBun.getPrice()).thenReturn(30.0f);
        when(mockIngredient1.getPrice()).thenReturn(15.0f);
        when(mockIngredient2.getPrice()).thenReturn(20.0f);
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        float actualPrice = burger.getPrice();

        assertEquals(95.0f, actualPrice, 0.01f); // 30 * 2 + 15 + 20 = 95
    }
}
