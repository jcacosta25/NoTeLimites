//
//  Event.swift
//  No te limites
//
//  Created by Griselda Epifanio on 12/8/16.
//  Copyright © 2016 Jose Epifanio. All rights reserved.
//

import Foundation

class Event
{
    private var date:String = ""
    private var name:String = ""
    private var place:String = ""
    private var people:String = ""
    
    //Seters
    public func setDate(date: String) {
        self.date = date
    }
    
    public func setName(name: String) {
        self.name = name
    }
    
    public func setPlace(place: String) {
        self.place = place
    }
    
    public func setPeople(people: String) {
        self.people = people
    }
    
    //Geters
    public func getDate()-> String {
        return self.date
    }
    
    public func setName()-> String {
        return self.name
    }
    
    public func getPlace()-> String {
        return self.place
    }
    
    public func setPeople()-> String {
        return self.people
    }
    
}

