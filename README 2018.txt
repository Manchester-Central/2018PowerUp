Autonomous Syntax 2018


Syntax (In text file):
	
Syntax (In Dashboard):
	Auto Stage X    |   Command; Value(s) ( , or .)   |   String

		X = the number of the stage; the order sequence value
		Command = The action performed by robot
		Value = Arguments passed into robot. If there is more
			than 1 value, separate by & sign

		. = Sequential command, run by itself
		, = Parallel command, run at same time 
		    as the next sequential command
			
Commands:
	Simple Commands 
		"Drive"		= Drive Straight (Positive Values drive forward)
		"Turn" 		= Turn (Positive Values turn counterclockwise)
		"Wait" 		= Wait
		"Lift" 		= Lift
		"Intake"	= Intake 		
		"Output"	= Scoring Cube 
		"Extend"	= Extend Arm	
		"Retract"	= Retract Arm 	

	Conditionals (Changes base on field data)
		"SwitchDrive"	= Drives based on which Switch you own
		"SwitchTurn"	= Turns based on which Switch you own

		"ScaleDrive"	= Drive based on which Scale you own
		"ScaleTurn"	= Turns based on which Scale you own

		The first argument (on the left) is run when you own the left structure
		The second argument (on the right) is run when you own the right structure

	Command Groups (Predefined series of Actions)
		"IntakeCube" 	= Lowers lift to floor, intakes cube, 
				  lifts the cube up and into the robot
		"OutputSwitch" 	= Lifts arm to switch height and outputs the cube
		"OutputScale" 	= Lifts arm to scale height and outputs the cube

Examples:
	"Drive;24."
	"SwitchTurn;90&-90."
	"Lift;36,"
	"Drive;48."
	"SwitchTurn;-90&90."
	"Drive;30."
	"OutputSwitch;."
