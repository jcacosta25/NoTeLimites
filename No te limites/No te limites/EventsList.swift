//
//  EventsList.swift
//  No te limites
//
//  Created by Griselda Epifanio on 12/8/16.
//  Copyright © 2016 Jose Epifanio. All rights reserved.
//

import Foundation

class EventsList
{
    private var events:[Events]? = nil
    
    public func setEvents(events: [Events]) {
        self.events = events
    }
    
    public func getEvents()-> [Events] {
        return self.events!
    }
}
