//
//  ProfileController.swift
//  No te limites
//
//  Created by Griselda Epifanio on 10/23/16.
//  Copyright © 2016 Jose Epifanio. All rights reserved.
//

import UIKit

class ProfileController: UIViewController {
    @IBOutlet weak var eventsBtn: UIButton!
    @IBOutlet weak var placesBtn: UIButton!
    let eventsOn: UIImage = UIImage(named: "profile_events_on_btn")!
    let placesOn: UIImage = UIImage(named: "profile_places_on_btn")!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func eventsBtnClick(_ sender: UIButton) {
        eventsBtn.setBackgroundImage(eventsOn, for: .highlighted)
    }

    @IBAction func placesBtnClick(_ sender: UIButton) {
        placesBtn.setBackgroundImage(placesOn, for: .highlighted)
    }
}
