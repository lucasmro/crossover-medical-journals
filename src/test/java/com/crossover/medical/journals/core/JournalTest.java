package com.crossover.medical.journals.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JournalTest {

    private Journal journal;

    @Before
    public void setUp() {
        this.journal = new Journal();
    }

    @Test
    public void testShouldCreateValidJournal() {
        journal.setId(1L);
        journal.setTopic(Topic.CARDIOLOGY);
        journal.setSubject("Coronary-Artery Bypass Grafting");
        journal.setAuthor("J.H. Alexander");
        journal.setYear(2016);
        journal.setFilename("/tmp/attachments/coronary-artery.pdf");

        Assert.assertEquals(1L, journal.getId(), 0);
        Assert.assertEquals(Topic.CARDIOLOGY, journal.getTopic());
        Assert.assertEquals("Coronary-Artery Bypass Grafting", journal.getSubject());
        Assert.assertEquals("J.H. Alexander", journal.getAuthor());
        Assert.assertEquals(2016, journal.getYear(), 0);
        Assert.assertEquals("/tmp/attachments/coronary-artery.pdf", journal.getFilename());
    }
}
