package es.upm.grise.profundizacion.subscriptionService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.upm.grise.profundizacion.exceptions.ExistingUserException;
import es.upm.grise.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;
import es.upm.grise.profundizacion.exceptions.NullUserException;

public class SubscriptionServiceTest {
	
	private SubscriptionService subscriptionService;
	
	@BeforeEach
	public void setUp() {
		subscriptionService = new SubscriptionService();
	}

	@Test
	public void smokeTest() {}

	@Test
	public void testAddSubscriberNullUser() {
		assertThrows(NullUserException.class, () -> {
			subscriptionService.addSubscriber(null);
		});
	}

	@Test
	public void testAddSubscriberExistingUser() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
		User user = new User();
		user.setDelivery(Delivery.DO_NOT_DELIVER);
		user.setEmail("test@example.com");
		subscriptionService.addSubscriber(user);
		assertThrows(ExistingUserException.class, () -> {
			subscriptionService.addSubscriber(user);
		});
	}

	@Test
	public void testAddSubscriberLocalDeliveryWithNonNullEmail() {
		User user = new User();
		user.setDelivery(Delivery.LOCAL);
		user.setEmail("test@example.com");
		
		assertThrows(LocalUserDoesNotHaveNullEmailException.class, () -> {
			subscriptionService.addSubscriber(user);
		});
	}

	@Test
	public void testAddSubscriberLocalDeliveryWithNullEmail() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
		User user = new User();
		user.setDelivery(Delivery.LOCAL);
		user.setEmail(null);
		subscriptionService.addSubscriber(user);
		assertTrue(subscriptionService.getSubscribers().contains(user));
	}

	@Test
	public void testAddSubscriberDoNotDeliver() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
		User user = new User();
		user.setDelivery(Delivery.DO_NOT_DELIVER);
		user.setEmail("test@example.com");
		subscriptionService.addSubscriber(user);
		assertTrue(subscriptionService.getSubscribers().contains(user));
	}
}
