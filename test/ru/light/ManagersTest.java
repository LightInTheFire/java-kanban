package ru.light;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

class ManagersTest {

    @Test
    void getDefaultHistoryTest() {
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }

    @Test
    void getDefaultTest() {
        Assertions.assertNotNull(Managers.getDefault());
    }
}