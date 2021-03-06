package com.tngtech.test.java.junit.dataprovider;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class DataProviderListArgAcceptanceTest {

    @DataProvider
    public static Object[][] dataProviderListArg() {
        // @formatter:off
        return new Object[][] {
            { Arrays.asList("a", "b"), "c" },
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider
    public void testListArg(List<String> list, String string) {
        // Expected:
        assertThat(list).doesNotContain(string);
    }

    @DataProvider
    public static Object[][] stringsData() {
        return new Object[][] {
                { Arrays.asList("string1", "stringValue"), "stringValue" }
        };
    }

    @Test
    @UseDataProvider("stringsData")
    public void test(List<String> strings, String expectedValue) {
        // Expected:
        assertThat(strings).contains(expectedValue);
    }

    public interface UnaryOperator<T> {
        T apply(T arg);
    }

    @DataProvider
    public static List<List<UnaryOperator<String>>> listOfListOfUnaryOperator() {
        return Collections.singletonList(Collections.<UnaryOperator<String>>singletonList(new UnaryOperator<String>() {
            @Override
            public String apply(String arg) {
                return "merged-" + arg;
            }
        }));
    }

    @Test
    @UseDataProvider("listOfListOfUnaryOperator")
    public void testListOfListOfUnaryOperator(UnaryOperator<String> operator) {
        assertThat(operator.apply("test")).isEqualTo("merged-test");
    }
}
