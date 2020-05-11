package final_exam;

import java.util.ArrayList;

public class ServerUpdater extends Thread {
	@Override 
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(1000);
				for(int i=0;i < Server.globalList.size();i++) {
					if(!Server.globalList.get(i).isExpired()) {
						Server.globalList.get(i).setSecondsLeft(Server.globalList.get(i).getSecondsLeft()-1); //decrement time every second
						Server.globalList.get(i).updateTimeLeft();
						if(Server.globalList.get(i).getSecondsLeft()==0) {
							Server.globalList.get(i).setExpired(true);
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}