import org.assertj.core.api.SoftAssertions;
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

@RunWith(MockitoJUnitRunner.class)
public class BurgerMockTests {
    private Burger burger;
    @Mock
    private Bun mockBun;
    @Mock
    private Ingredient mockIngredientOne;
    @Mock
    private Ingredient mockIngredientTwo;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        burger = new Burger();
        mockBun = new Bun("Булка", 10f);
        mockIngredientOne = new Ingredient(IngredientType.FILLING, "Котлета", 5.0f);
        mockIngredientTwo = new Ingredient(IngredientType.SAUCE, "Начинка", 75f);
    }
    @Test
    public void setBunsTest(){

        burger.setBuns(mockBun);

        assertEquals(10.0f,mockBun.getPrice(),0.01f);
    }
    @Test
    public void addIngredientTest(){
        SoftAssertions softly = new SoftAssertions();

        burger.addIngredient(mockIngredientOne);
        softly.assertThat(1).isEqualTo(burger.ingredients.size());
        softly.assertThat(mockIngredientOne).isEqualTo(burger.ingredients.get(0));
        softly.assertThat("Котлета").isEqualTo(burger.ingredients.get(0).getName());
        softly.assertThat(5.0f).isEqualTo(burger.ingredients.get(0).getPrice());
        softly.assertAll();
    }
    @Test
    public void removeIngredientTest(){

        burger.addIngredient(mockIngredientOne);
        burger.removeIngredient(0);

        assertEquals(0,burger.ingredients.size());
    }
    @Test
    public void moveIngredientsTest(){
        SoftAssertions softly = new SoftAssertions();

        burger.addIngredient(mockIngredientOne);
        burger.addIngredient(mockIngredientTwo);

        burger.moveIngredient(0,1);

        softly.assertThat(2).isEqualTo(burger.ingredients.size());
        softly.assertThat(mockIngredientOne).isEqualTo(burger.ingredients.get(1));
        softly.assertThat(mockIngredientTwo).isEqualTo(burger.ingredients.get(0));
        softly.assertAll();
    }
    @Test
    public void testGetReceiptWithMultipleIngredients() {

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredientOne);
        burger.addIngredient(mockIngredientTwo);

        String receipt = burger.getReceipt();

        String expectedReceipt = String.format("(==== %s ====)%n", mockBun.getName()) +
                String.format("= %s %s =%n", mockIngredientOne.getType().toString().toLowerCase(),
                        mockIngredientOne.getName()) +
                String.format("= %s %s =%n", mockIngredientTwo.getType().toString().toLowerCase(),
                        mockIngredientTwo.getName()) +
                String.format("(==== %s ====)%n", mockBun.getName()) +
                String.format("%nPrice: %f%n", burger.getPrice());

        assertEquals(expectedReceipt, receipt);
    }
}
