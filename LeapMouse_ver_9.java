import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Tool;
import com.leapmotion.leap.Vector;

public class LeapMouse_ver_9 extends Listener{
	public Robot robot;
	public boolean button1IsClicked;
	public boolean button2IsClicked;
	public boolean button3IsClicked;
	
	// To get the number of extended pointables//
	public int extendedFingerNumber(Hand hand){
		int extendedFingers = 0;
		for(Finger finger : hand.fingers()){
			if(finger.isExtended()) extendedFingers++;
		}
		return extendedFingers;
	}
	// To get the number of extended pointables//
	
	@Override
	public void onInit(Controller controller){
		//create robot//
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//create robot//
		
		//set the default variable//
		button1IsClicked = false;
		button2IsClicked = false;
		button3IsClicked = false;
		//set the default variable//
		
		System.out.println("LeapMouse Started");
	}
	
	@Override
	public void onFrame(Controller controller){
		Frame frame = controller.frame();
		Frame frame1 = controller.frame(1);
		Frame frame4 = controller.frame(4);
		
		if(frame.hands().count() == 0){
//////////////////////// When Using Tool ////////////////////////
			//button2 release//
			if(button2IsClicked && frame.tools().count() > 0){
				robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
				System.out.println("button2 released");
				button2IsClicked = false;
			}
			//button2 release//
			
			//Pointer Move//
			if(frame.tools().count()>0){				
				Tool tool = frame.tools().frontmost();
				Tool tool1 = frame1.tools().frontmost();
				
				double currentx = MouseInfo.getPointerInfo().getLocation().getX();
				double currenty = MouseInfo.getPointerInfo().getLocation().getY();
				float x = tool.tipPosition().getX() - tool1.tipPosition().getX();
				float y = tool.tipPosition().getY() - tool1.tipPosition().getY();
				if(button1IsClicked){
					if(((x*6)>1 || (x*6)<-1) && ((y*-6)>1) || (y*-6)<-1){
						robot.mouseMove((int) (currentx + x*6+0.5), (int) (currenty - y*6+0.5));
					}
				}
				else{
					robot.mouseMove((int) (currentx + x*6+0.5), (int) (currenty - y*6+0.5));
				}
			}
			//Pointer Move//
			
			else{
				//Button1 release//
				if(button1IsClicked){
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					System.out.println("button1 release");
					button1IsClicked = false;
				}
				//Button1 release//
			}
//////////////////////// When Using Tool ////////////////////////
		}
		
		
		
		
		else if(frame.hands().count() == 1){
			Hand hand = frame.hands().get(0);
			Hand hand1 = frame1.hands().get(0);
			Hand hand4 = frame4.hands().get(0);
			
//////////////////////// When Using Tool ////////////////////////
			if(frame.tools().count() > 0){
				//button2 release//
				if(button2IsClicked && extendedFingerNumber(hand)<4){
					robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
					System.out.println("button2 released");
					button2IsClicked = false;
				}
				//button2 release//
				
				//Pointer Move//
				Tool tool = frame.tools().frontmost();
				Tool tool1 = frame1.tools().frontmost();
				
				double currentx = MouseInfo.getPointerInfo().getLocation().getX();
				double currenty = MouseInfo.getPointerInfo().getLocation().getY();
				float x = tool.tipPosition().getX() - tool1.tipPosition().getX();
				float y = tool.tipPosition().getY() - tool1.tipPosition().getY();
				if(button1IsClicked){
					if(((x*6)>1 || (x*6)<-1) && ((y*-6)>1) || (y*-6)<-1){
						robot.mouseMove((int) (currentx + x*6+0.5), (int) (currenty - y*6+0.5));
					}
				}
				else{
					robot.mouseMove((int) (currentx + x*6+0.5), (int) (currenty - y*6+0.5));
				}
				//Pointer Move//
				
				if(extendedFingerNumber(hand) < 4){
					//Button1 click and release//
					Finger leftFinger = hand.fingers().frontmost();
					Vector tip = leftFinger.tipPosition();
					Finger leftFinger4 = hand4.fingers().frontmost();
					Vector tip4 = leftFinger4.tipPosition();
					
					float difference = tip.getY() - tip4.getY();
					
					if((difference<10 && difference>0) || (difference>-27 && difference<0)){
						difference = 0;
					}
						
					if(!button1IsClicked && (difference<0)){
						robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
						System.out.println("button1 click");
						button1IsClicked = true;
					}
					else if(button1IsClicked && difference>0){
						robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
						System.out.println("button1 release");
						button1IsClicked = false;
					}
					//Button1 click and release//
				}
				
				//button3 click and release//
				else if(!button3IsClicked && hand.scaleFactor(frame1)>1.23){
					robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
					System.out.println("button3 click");
					button3IsClicked = true;
				}
				else if(button3IsClicked && hand.scaleFactor(frame1)<1.23){
					robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
					System.out.println("button3 release");
					button3IsClicked = false;
				}
				//button3 click and release//
			}
//////////////////////// When Using Tool ////////////////////////			
			
//////////////////////// When Using Finger ////////////////////////
			else if(extendedFingerNumber(hand) < 4){
				//button2 release//
				if(button2IsClicked && extendedFingerNumber(hand) < 4){
					robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
					System.out.println("button2 released");
					button2IsClicked = false;
				}
				//button2 release//
				
				//button1 release//
				if(button1IsClicked){
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					System.out.println("button1 release");
					button1IsClicked = false;
				}
				//Button1 release//
				
				//Pointer Move//
				if(hand.isRight() && extendedFingerNumber(hand) < 4){
					Finger finger = hand.fingers().frontmost();
					Finger finger1 = hand1.fingers().frontmost();
				
					double currentx = MouseInfo.getPointerInfo().getLocation().getX();
					double currenty = MouseInfo.getPointerInfo().getLocation().getY();
					float x = finger.tipPosition().getX() - finger1.tipPosition().getX();
					float y = finger.tipPosition().getY() - finger1.tipPosition().getY();
					if(button1IsClicked){
						if(((x*6)>1 || (x*6)<-1) && ((y*-6)>1) || (y*-6)<-1){
							robot.mouseMove((int) (currentx + x*6+0.5), (int) (currenty - y*6+0.5));
						}
					}
					else{
						robot.mouseMove((int) (currentx + x*6+0.5), (int) (currenty - y*6+0.5));
					}
				}	
				//Pointer Move//
			}
//////////////////////// When Using Finger ////////////////////////
		}
		
		
		
		
		else if(frame.hands().count() == 2){
//////////////////////// When Using Finger ////////////////////////
			//Hand instance creating//
			Hand leftHand;
			Hand rightHand;
			
			Hand leftHand1;
			Hand rightHand1;
			
			Hand leftHand4;
			
			if(frame.hands().get(0).isLeft()){
				leftHand = frame.hands().get(0);
				rightHand = frame.hands().get(1);
				
				leftHand1 = frame1.hands().get(0);
				rightHand1 = frame1.hands().get(1);
				
				leftHand4 = frame4.hands().get(0);
			}
			else{
				leftHand = frame.hands().get(1);
				rightHand = frame.hands().get(0);
				
				leftHand1 = frame1.hands().get(1);
				rightHand1 = frame1.hands().get(0);
					
				leftHand4 = frame4.hands().get(1);
			}
			//Hand instance creating//
			
			
			if(extendedFingerNumber(rightHand) < 4){
				//button2 release//
				if(button2IsClicked && extendedFingerNumber(leftHand)<4){
					robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
					System.out.println("button2 released");
					button2IsClicked = false;
				}
				//button2 release//
				
				//Pointer Move//
				Finger finger = rightHand.fingers().frontmost();
				Finger finger1 = rightHand1.fingers().frontmost();
				
				double currentx = MouseInfo.getPointerInfo().getLocation().getX();
				double currenty = MouseInfo.getPointerInfo().getLocation().getY();
				float x = finger.tipPosition().getX() - finger1.tipPosition().getX();
				float y = finger.tipPosition().getY() - finger1.tipPosition().getY();
				if(button1IsClicked){
					if(((x*6)>1 || (x*6)<-1) && ((y*-6)>1) || (y*-6)<-1){
						robot.mouseMove((int) (currentx + x*6+0.5), (int) (currenty - y*6+0.5));
					}
				}
				else{
					robot.mouseMove((int) (currentx + x*6+0.5), (int) (currenty - y*6+0.5));
				}
				//Pointer Move//
				
				if(extendedFingerNumber(leftHand) < 4){
					//Button1 click and release//
					Finger leftFinger = leftHand.fingers().frontmost();
					Vector tip = leftFinger.tipPosition();
					Finger leftFinger4 = leftHand4.fingers().frontmost();
					Vector tip1 = leftFinger4.tipPosition();
					
					float difference = tip.getY() - tip1.getY();
					
					if((difference<10 && difference>0) || (difference>-27 && difference<0)){
						difference = 0;
					}
						
					if(!button1IsClicked && (difference<0)){
						robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
						System.out.println("button1 click");
						button1IsClicked = true;
					}
					else if(button1IsClicked && difference>0){
						robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
						System.out.println("button1 release");
						button1IsClicked = false;
					}
					//Button1 click and release//
				}
				
				//button3 click and release//
				else if(!button3IsClicked && leftHand.scaleFactor(frame1)>1.23){
					robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
					System.out.println("button3 click");
					button3IsClicked = true;
				}
				else if(button3IsClicked && leftHand.scaleFactor(frame1)<1.23){
					robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
					System.out.println("button3 release");
					button3IsClicked = false;
				}
				//button3 click and release//
			}
			else if(extendedFingerNumber(rightHand) > 3){
				//To determine what this action is for//
				Vector leftNormalVector = leftHand.palmNormal();
				Vector rightNormalVector = rightHand.palmNormal();
				
				if(leftNormalVector.angleTo(Vector.right()) < Math.PI/3){
					leftNormalVector = Vector.right();
				}
				if(rightNormalVector.angleTo(Vector.left()) < Math.PI/3){
					rightNormalVector = Vector.left();
				}
				if(leftNormalVector.angleTo(Vector.forward()) < Math.PI/3){
					leftNormalVector = Vector.forward();
				}
				if(rightNormalVector.angleTo(Vector.forward()) < Math.PI/3){
					rightNormalVector = Vector.forward();
				}
				//To determine what this action is for//
				
				//Zooming//
				if(leftNormalVector.equals(Vector.right()) && rightNormalVector.equals(Vector.left())){
					//button2 release//
					if(button2IsClicked && extendedFingerNumber(leftHand)<4){
						robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
						System.out.println("button2 released");
						button2IsClicked = false;
					}
					//button2 release//
					
					//wheel moving//
					int wheelScaleFactor = (int) -(Math.log(frame.scaleFactor(frame1))/Math.log(1.03));
					
					if(frame.scaleFactor(frame1) > 1 && wheelScaleFactor != 0){
						System.out.println("wheel up");
						robot.mouseWheel(wheelScaleFactor);
					}
					else if(frame.scaleFactor(frame1) <= 1 && wheelScaleFactor != 0){
						System.out.println("wheel down");
						robot.mouseWheel(wheelScaleFactor);
					}
					//wheel moving//
				}
				//Zooming//
				
				//Panning//
				else if(leftNormalVector.equals(Vector.forward()) && rightNormalVector.equals(Vector.forward())){
					//button2 click
					if(!button2IsClicked && extendedFingerNumber(leftHand)>3){
						robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
						System.out.println("button2 clicked");
						button2IsClicked = true;
					}
					//button2 click//

					//button2 release//
					else if(button2IsClicked && extendedFingerNumber(leftHand)<4){
						robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
						System.out.println("button2 released");
						button2IsClicked = false;
					}
					//button2 release//
					
					//button2 click and moving//
					else if(button2IsClicked && leftHand.grabStrength()<0.3){
						//pointer move//
						int currentx = (int) MouseInfo.getPointerInfo().getLocation().getX();
						int currenty = (int) MouseInfo.getPointerInfo().getLocation().getY();
						
						int x = (int) ((leftHand.translation(frame1).getX()+rightHand.translation(frame1).getX())/2);
						int y = (int) ((leftHand.translation(frame1).getY()+rightHand.translation(frame1).getY())/2);		
						robot.mouseMove(currentx + x*4, currenty - y*4);
						//pointer move//
					}
					//button2 click and moving//
				}
				//Panning
			}
//////////////////////// When Using Finger ////////////////////////
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Create a sample Listener and controller
		LeapMouse_ver_9 listener = new LeapMouse_ver_9();
		Controller controller = new Controller();

		//Have the sample listener receive events from the controller        
		controller.addListener(listener);
				
		try{
			System.in.read();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		//Remove the sample listener when done
		controller.removeListener(listener);				
	}
}
