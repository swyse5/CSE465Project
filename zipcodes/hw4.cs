// Stuart Wyse
// CSE 465 - HW4

using System.IO;
using System;
using System.Collections.Generic;

class Program
{
  static void Main() {
    CommonCityNames();
    LatLon();
    CityStates();
  }

  //-------------------------------------------------------
  // CommonCityNames.txt

  static void CommonCityNames() {
    string line;
    System.IO.StreamReader file = new System.IO.StreamReader("states.txt");

    // List to hold states in states.txt
    List<string> states = new List<string>();

    // add states to states list
    while((line = file.ReadLine()) != null) {
      states.Add(line);
    }

    System.IO.StreamReader zipcodes = new System.IO.StreamReader("zipcodes.txt");
    List<string> CommonCityNames = new List<string>();

    // if city is in state that is in states.txt, add it to CommonCityNames
    while((line = zipcodes.ReadLine()) != null) {
      string[] words = line.Split('\t');
      foreach(string state in states) {
        if(words[4] == state) {
          CommonCityNames.Add(words[3]);
        }
      }
    }

    // sort List, and write List to CommonCityNames.txt
    CommonCityNames.Sort();
    using(StreamWriter writeText = new StreamWriter("CommonCityNames.txt")) {
      CommonCityNames.ForEach(delegate(string city) {
        writeText.WriteLine(city);
        });
      }

      file.Close();
      zipcodes.Close();
    }

    // --------------------------------------------------
    // LatLon.txt

    static void LatLon() {
      string line2;
      System.IO.StreamReader file2 = new System.IO.StreamReader("zips.txt");

      // List to hold states in states.txt
      List<string> zips = new List<string>();

      // add states to states list
      while((line2 = file2.ReadLine()) != null) {
        zips.Add(line2);
      }

      System.IO.StreamReader zipcodes2 = new System.IO.StreamReader("zipcodes.txt");
      List<string> LatLon = new List<string>();

      // add zip, lat, and lon to LatLon list
      // use zip to make sure no duplicates are added
      while((line2 = zipcodes2.ReadLine()) != null) {
        string[] words2 = line2.Split('\t');
        foreach(string zip in zips) {
          if(words2[1] == zip && !LatLon.Contains(words2[1])) {
            LatLon.Add(words2[1]);
            LatLon.Add(words2[6]);
            LatLon.Add(words2[7]);
          }
        }
      }

      using (StreamWriter writeText2 = new StreamWriter("LatLon.txt")) {

        // add lat and lon to LatLon.txt
        int counter = 0;
        foreach(string entry in LatLon) {
          if(counter == 1 || counter == 2) {
            writeText2.Write(entry + ' ');
          }
          if(counter >= 2) {
            writeText2.Write('\n');
            counter = 0;
          } else {
            counter++;
          }
        }
      }
      file2.Close();
      zipcodes2.Close();
    }

    // ----------------------------------------------------
    // CityStates.txt

    static void CityStates() {
      string line3;
      System.IO.StreamReader file3 = new System.IO.StreamReader("cities.txt");

      // List to hold cities in cities.txt
      List<string> cities = new List<string>();

      // add cities to cities list
      while((line3 = file3.ReadLine()) != null) {
        cities.Add(line3);
      }

      System.IO.StreamReader zipcodes3 = new System.IO.StreamReader("zipcodes.txt");
      List<string> CityStates = new List<string>();

      // for each city, check each line of zipcodes for matching city
      // if city matches, add it to CityStates
      foreach(string city in cities) {
        // move to beginning of zipcodes.txt for each city
        zipcodes3.DiscardBufferedData();
        zipcodes3.BaseStream.Seek(0, System.IO.SeekOrigin.Begin);

        CityStates.Add("\n");
        CityStates.Add(city);
        while((line3 = zipcodes3.ReadLine()) != null) {
          string[] words3 = line3.Split('\t');
          // add states to CityStates list if they have the given city
          if(words3[3] == city.ToUpper()) {
            CityStates.Add(words3[4]);
          }
        }
      }

      List<string> CityStates2 = new List<string>();

      using (StreamWriter writeText3 = new StreamWriter("CityStates.txt")) {

        foreach(string entry in CityStates) {
          // if entry is the name of the city
          if(entry.Length != 2) {
            CityStates2.Sort();
            foreach(string cityState in CityStates2) {
              writeText3.Write(cityState + " ");
            }
            // clear the list for the next city
            CityStates2.Clear();
            writeText3.Write(entry);
            writeText3.Write(" ");

          } else if(entry.Length == 2 && !CityStates2.Contains(entry)) {
            CityStates2.Add(entry);
          }
        }
        // at end of CityStates, sort and write CityStates2
        CityStates2.Sort();
        foreach(string cityState in CityStates2) {
          writeText3.Write(cityState + " ");
        }

      }
      file3.Close();
      zipcodes3.Close();
    }
  }
