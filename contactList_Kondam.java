/*
 * Siddharth Kondam 
 * This project uses a TreeMap to represent a contact list address book, with the key being the alias and the corresponding value held by a TreeSet. 
 * Through this, we are able to add members to a certain groupName, remove entire groups, expand aliases, and more. 
 */

import java.util.*;

public class contactList_Kondam {

	TreeMap<String,TreeSet> allContacts = new TreeMap<String, TreeSet>(); 


	//adds a member to the group
	public void add(String groupName, String member) {

		if(groupName == null || member == null)
			return; 

		//if alias isn't made, add new treeSet to the TreeMap 
		if(allContacts.get(groupName) == null) {
			TreeSet<String> members = new TreeSet<String>();
			members.add(member);
			allContacts.put(groupName,members);	
		}

		//add member to the existing treeSet, then add that to the TreeMap
		else {
			allContacts.get(groupName).add(member); 
			allContacts.put(groupName, allContacts.get(groupName));
		}
	}

	//removes a member from the group or return false if they weren't a member to begin with or group didn't exist
	public boolean remove(String groupName, String member) {

		if(groupName == null)
			return false; 	

		TreeSet<String> members = allContacts.get(groupName); 

		//removes member from set and adds modified set to the map if the member is contained in the set
		if(members.contains(member)) {
			members.remove(member);
			allContacts.put(groupName, members);
			return true; 
		}

		return false;  
	}

	//removes entire group from the contact list or returns false if the group isn't found
	public boolean remove(String groupName) {

		boolean removed = false; 

		if(groupName == null)
			return false; 

		//if the groupName is in the alias
		if(allContacts.containsKey(groupName)) {
			allContacts.remove(groupName); 
			removed = true; 
		}

		//check if the groupName is contained in each of the sets 
		for(String traverse: allContacts.keySet()) {
			if(remove(traverse,groupName)) 
				removed = true; 
		}

		return removed; 
	}

	//prints all group names and members
	public void print() {

		System.out.print("Alias \t Members of the Group");
		System.out.println();

		for(String travAlias: allContacts.keySet()) 
			System.out.println(travAlias + "\t" + allContacts.get(travAlias));
	}

	//helper method that adds all items to the set to the end of the queue
	public void appenSettoQueue(Set<String> items, Queue<String> queues) {
		if(items!= null && queues!= null){
			for(String traverse: items)
				queues.add(traverse);
		}
	}


	//builds a set of all email addresses with all subsequent aliases removed
	public Set<String> expandAlias(String alias){

		TreeSet<String> members = new TreeSet<String>(); 
		Queue<String> addToSet = new LinkedList<String>(); 

		//if the alias is one of the keys, add all of the items in the set to the queue
		if(allContacts.containsKey(alias) && alias!=null) 
			appenSettoQueue(allContacts.get(alias),addToSet); 


		while(!addToSet.isEmpty()) {

			//adds the members of the alias to the queue and removes the member inspected
			if(addToSet.peek().indexOf("@")==-1) {
				appenSettoQueue(allContacts.get(addToSet.peek()),addToSet);  	
				addToSet.remove(); 
			}

			//removes the element from the queue and adds it to the set 
			else
				members.add(addToSet.remove());
		}

		return members; 
	}
}
