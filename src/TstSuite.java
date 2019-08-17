package controller;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import database.Dbtst;
import database.Dbtst2;
import database.Dbtst3;

import org.junit.Test;



@RunWith(Suite.class)

@Suite.SuiteClasses({
	   Player2tst.class,
	   Player1tst.class,
	   Dbtst3.class,
	   Dbtst2.class,
	   Dbtst.class
	})


public class TstSuite {

	

}
