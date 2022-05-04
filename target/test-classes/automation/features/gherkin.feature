Feature: EXYM-711 As a Clinician, 
         I want a page dedicated to the overview of a client, 
         so I do not need to click around to see all of their information.
         

		 Background
			Given I am a Clinician: 
			When I view the vNext homepage, 
			want a page dedicated to the overview of a client, 
			so I do not need to click around to see all of their information.
	
	
	Scenario: Sort column by Service date
		Given I am a clinician user
		 When I go to the main page Exym vNext portal
		  And I click on a client name in the client table view on the dashboard I am taken to new client details page
		 Then I should see a photo icon in the top left corner of the page
		  And I should see to right of the photo icon the first and last name of my client
          And I can click on Dashboard and return back to the dashboard page


		

		
		
		
		
		