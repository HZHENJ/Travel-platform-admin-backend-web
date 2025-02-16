package com.example.backendweb.Entity.Admin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import jakarta.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class AdminTest {

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        // Clear the persistence context before each test
        entityManager.clear();
    }

    @AfterEach
    void cleanup() {
        // Clean up after each test
        entityManager.clear();
    }

    @Test
    public void testCreateAdmin() {
        // Create a new admin
        Admin admin = new Admin();
        admin.setUsername("admin1");
        admin.setPassword("password123");

        // Save the admin
        Admin savedAdmin = entityManager.persistAndFlush(admin);

        // Clear the persistence context
        entityManager.clear();

        // Retrieve the admin and verify
        Admin retrievedAdmin = entityManager.find(Admin.class, savedAdmin.getId());
        assertNotNull(retrievedAdmin);
        assertEquals("admin1", retrievedAdmin.getUsername());
        assertEquals("password123", retrievedAdmin.getPassword());
    }

    @Test
    public void testUniqueUsernameConstraint() {
        // Create first admin
        Admin admin1 = new Admin();
        admin1.setUsername("admin1");
        admin1.setPassword("password123");
        entityManager.persistAndFlush(admin1);

        // Create second admin with same username
        Admin admin2 = new Admin();
        admin2.setUsername("admin1");
        admin2.setPassword("different_password");

        // Attempt to save second admin should throw exception
        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(admin2);
        });
    }

    @Test
    public void testNullableConstraints() {
        Admin admin = new Admin();

        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(admin);
        });
    }

    @Test
    public void testUpdateAdmin() {
        // Create an admin
        Admin admin = new Admin();
        admin.setUsername("admin1");
        admin.setPassword("password123");
        Admin savedAdmin = entityManager.persistAndFlush(admin);

        // Clear persistence context
        entityManager.clear();

        // Update the admin
        Admin adminToUpdate = entityManager.find(Admin.class, savedAdmin.getId());
        adminToUpdate.setPassword("newpassword456");
        entityManager.persistAndFlush(adminToUpdate);

        // Clear persistence context again
        entityManager.clear();

        // Verify the update
        Admin updatedAdmin = entityManager.find(Admin.class, savedAdmin.getId());
        assertEquals("newpassword456", updatedAdmin.getPassword());
        assertEquals("admin1", updatedAdmin.getUsername());
    }

    @Test
    public void testDeleteAdmin() {
        // Create an admin
        Admin admin = new Admin();
        admin.setUsername("admin1");
        admin.setPassword("password123");
        Admin savedAdmin = entityManager.persistAndFlush(admin);

        // Delete the admin
        entityManager.remove(savedAdmin);
        entityManager.flush();

        // Verify deletion
        Admin deletedAdmin = entityManager.find(Admin.class, savedAdmin.getId());
        assertNull(deletedAdmin);
    }
}