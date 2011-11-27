package libheaven.core.marc

import org.junit.Test
import org.junit.Assert

class MultiLevelDescrTest extends RecordTestBase {

  /**
   * Многоуровневое описание
   */
  @Test
  def test1 = {
    val example = rusmarcRecord(Array(
      ("010", ' ', ' ', Array(
        ('a', "5-88528-063-0")
      )),
      ("200", '1', ' ', Array(
        ('a', "Поэмы 1825-1833")
      )),
      ("210", ' ', ' ', Array(
        ('a', "М."),
        ('c', "Воскресенье"),
        ('d', "1994")
      )),
      ("215", ' ', ' ', Array(
        ('a', "570 с."),
        ('c', "ил."))),
      ("461", ' ', '0', Array(
        ('1', "2001#$aПолное собрание сочинений$vТ. 5")
      ))
    ))
    val rec_461 = rusmarcRecord(Array(
      ("200", '1', ' ', Array(
        ('a', "Полное собрание сочинений"),
        ('b', "Текст"),
        ('e', "в 8 т."),
        ('f', "А.С. Пушкин")
      )),
      ("210", ' ', ' ', Array(
        ('a', "М."),
        ('c', "Воскресенье"),
        ('d', "1994")
      )),
      ("225", ' ', ' ', Array(
        ('a', "Литературные памятники")
      ))
    ))
    val expected = "Полное собрание сочинений [Текст] : в 8 т. / А.С. Пушкин. – М. : Воскресенье, 1994. – (Литературные памятники)\nТ. 5 : Поэмы 1825-1833. – 1994. – 570 с. : ил. – ISBN 5-88528-063-0."
    val actual = example.multiLevelDescription(rec_461)
    println(actual)
    Assert.assertEquals(expected, actual)
  }

 }