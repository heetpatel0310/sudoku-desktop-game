package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sudoku.model.models.SudokuField;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SudokuFieldExtendedTest {

    @Test
    void testDefaultConstructorValueIsZero() {
        SudokuField field = new SudokuField();
        assertEquals(0, field.getValue());
    }

    @Test
    void testParameterizedConstructor() {
        SudokuField field = new SudokuField(5);
        assertEquals(5, field.getValue());
    }

    @Test
    void testSetValueAndGetValue() {
        SudokuField field = new SudokuField();
        field.setValue(7);
        assertEquals(7, field.getValue());
    }

    @Test
    void testPropertyChangeListenerFires() {
        SudokuField field = new SudokuField(3);
        AtomicReference<PropertyChangeEvent> capturedEvent = new AtomicReference<>();

        field.addPropertyChangeListener(capturedEvent::set);
        field.setValue(8);

        assertNotNull(capturedEvent.get());
        assertEquals("value-changed", capturedEvent.get().getPropertyName());
        assertEquals(3, capturedEvent.get().getOldValue());
        assertEquals(8, capturedEvent.get().getNewValue());
    }

    @Test
    void testPropertyChangeListenerFiresOnEveryChange() {
        SudokuField field = new SudokuField(0);
        AtomicInteger callCount = new AtomicInteger(0);

        field.addPropertyChangeListener(evt -> callCount.incrementAndGet());

        field.setValue(1);
        field.setValue(2);
        field.setValue(3);

        assertEquals(3, callCount.get());
    }

    @Test
    void testRemovePropertyChangeListener() {
        SudokuField field = new SudokuField(0);
        AtomicInteger callCount = new AtomicInteger(0);
        PropertyChangeListener listener = evt -> callCount.incrementAndGet();

        field.addPropertyChangeListener(listener);
        field.setValue(1);
        assertEquals(1, callCount.get());

        field.removePropertyChangeListener(listener);
        field.setValue(2);
        assertEquals(1, callCount.get());
    }

    @Test
    void testEqualsSameValue() {
        SudokuField field1 = new SudokuField(5);
        SudokuField field2 = new SudokuField(5);
        assertEquals(field1, field2);
    }

    @Test
    void testEqualsDifferentValue() {
        SudokuField field1 = new SudokuField(5);
        SudokuField field2 = new SudokuField(3);
        assertNotEquals(field1, field2);
    }

    @Test
    void testEqualsNull() {
        SudokuField field = new SudokuField(5);
        assertNotEquals(null, field);
    }

    @Test
    void testEqualsDifferentClass() {
        SudokuField field = new SudokuField(5);
        assertNotEquals("5", field);
    }

    @Test
    void testHashCodeConsistency() {
        SudokuField field1 = new SudokuField(5);
        SudokuField field2 = new SudokuField(5);
        assertEquals(field1.hashCode(), field2.hashCode());
    }

    @Test
    void testHashCodeDiffers() {
        SudokuField field1 = new SudokuField(1);
        SudokuField field2 = new SudokuField(9);
        assertNotEquals(field1.hashCode(), field2.hashCode());
    }

    @Test
    void testToStringContainsValue() {
        SudokuField field = new SudokuField(4);
        String str = field.toString();
        assertNotNull(str);
        assertTrue(str.contains("4"));
    }

    @Test
    void testCloneProducesEqualButDistinctObject() {
        SudokuField original = new SudokuField(7);
        SudokuField cloned = original.clone();

        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    void testCloneIsIndependent() {
        SudokuField original = new SudokuField(7);
        SudokuField cloned = original.clone();

        cloned.setValue(3);

        assertEquals(7, original.getValue());
        assertEquals(3, cloned.getValue());
        assertNotEquals(original, cloned);
    }

    @Test
    void testCompareToEqual() {
        SudokuField field1 = new SudokuField(5);
        SudokuField field2 = new SudokuField(5);
        assertEquals(0, field1.compareTo(field2));
    }

    @Test
    void testCompareToLessThan() {
        SudokuField field1 = new SudokuField(3);
        SudokuField field2 = new SudokuField(7);
        assertTrue(field1.compareTo(field2) < 0);
    }

    @Test
    void testCompareToGreaterThan() {
        SudokuField field1 = new SudokuField(9);
        SudokuField field2 = new SudokuField(1);
        assertTrue(field1.compareTo(field2) > 0);
    }
}
