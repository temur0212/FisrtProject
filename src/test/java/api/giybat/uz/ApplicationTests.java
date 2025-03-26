package api.giybat.uz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {



	@Autowired
	private HetBillingService hetBillingService;

	@Test
	void contextLoads() {
		System.out.println(hetBillingService.getOverview("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJBeEtsZld2b3R1YXZLMmwwUHdoYUtMb29RT0RvaUlkamFyMXlDRWJCWTVFIn0.eyJleHAiOjE3NDE2NzEzMzgsImlhdCI6MTc0MTIzOTMzOCwianRpIjoiMmI3MzY0OTQtMTdmNS00Y2FjLTlhNDUtZmY4ODdmMTk1NjkyIiwiaXNzIjoiaHR0cDovLzE3Mi4xNi44Mi44OjgwODAvcmVhbG1zL0hldEtleWNsb2FrIiwic3ViIjoiZmE4MjkzMGEtNjRmMi00M2I4LWI0M2QtZTg0Y2Q2ODg3NjQ4IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiaGV0LWxvZ2luIiwic2Vzc2lvbl9zdGF0ZSI6ImFjYzA2NGFhLTk0NzYtNDE5Ny1hZjE5LTJmM2RmZWUwN2ExMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLWhldGtleWNsb2FrIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsInZpZXctcmVhbG0iLCJtYW5hZ2UtaWRlbnRpdHktcHJvdmlkZXJzIiwiaW1wZXJzb25hdGlvbiIsInJlYWxtLWFkbWluIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJoZXQtbG9naW4iOnsicm9sZXMiOlsicmVhbG0tY3VzdG9tLXJvbGUiLCJ1bWFfcHJvdGVjdGlvbiJdfSwiYnJva2VyIjp7InJvbGVzIjpbInJlYWQtdG9rZW4iXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJ2aWV3LWFwcGxpY2F0aW9ucyIsInZpZXctY29uc2VudCIsInZpZXctZ3JvdXBzIiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJtYW5hZ2UtY29uc2VudCIsImRlbGV0ZS1hY2NvdW50Iiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwaG9uZSBwcm9maWxlIiwic2lkIjoiYWNjMDY0YWEtOTQ3Ni00MTk3LWFmMTktMmYzZGZlZTA3YTExIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJ1c2VyX2lkIjoiMjA0MDkyIiwiY29hdG9fY29kZSI6IjMzMjMzIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiMzMyMzMxMDI0MTY0NCJ9.cF5oCp23ng_WFXHdewbhWNkHhtXsDM4BZ301hrcahzU3vTdAjb6KpOIHcHfaFdlqfDgToTMRuYb9IgX1KDFgxNy5kYsy4vy_8jFwtWqjf6uQ-lA4L36cz0vENG0zBqRBEHedEv7Gvg-prVAY0nOPlEn6bGArn4yfPvuGD8lYRIcTt2cd8_wYuK-T6h-N7YOXDlJsxQRF3o-9bgN3IWxOS67p-JewfsqVCSG-1ygOb_X6fhB5OK3_kRMqQ7rbF7hv-eQeA7aIQQTP9gMkur3NNdM2QWCRk_GCCOYB1x1rZJlmBipEbqcfka8qsfnnj-VpGIp4JxTFW40yQFPZRqQsaQ","2025-03-01")	);
	}

}
