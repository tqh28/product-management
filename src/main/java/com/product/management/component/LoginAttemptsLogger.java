package com.product.management.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoginAttemptsLogger {
	
	private static final Logger logger = LogManager.getLogger(LoginAttemptsLogger.class);

	@EventListener
	public void auditEventHappened (AuditApplicationEvent auditApplicationEvent) {
		
		AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();
		logger.info("Principal: " + auditEvent.getPrincipal() + " - " + auditEvent.getType());
	}
}
