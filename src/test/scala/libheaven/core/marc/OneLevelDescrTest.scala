package libheaven.core.marc

import org.junit.Test
import org.junit.Assert

class OneLevelDescrTest extends RecordTestBase {

  /**
   * Одноуровневое под общим заглавием
   */
  @Test
  def test1 = {
    val example = rusmarcRecord(Array(
      ("010", ' ', ' ', Array(
        ('a', "5-88528-053-5")
      )),
      ("200", '1', ' ', Array(
        ('a', "Лицейские стихотворения")
      )),
      ("215", ' ', ' ', Array(
        ('a', "463 с."),
        ('c', "ил."))),
      ("461", ' ', '0', Array(
        ('1', "2001#$aПолное собрание сочинений$vТ. 1")
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
      ))
    ))
    val expected = "Полное собрание сочинений [Текст]. В 8 т. Т. 1. Лицейские стихотворения / А.С. Пушкин. – М. : Воскресенье, 1994. – 463 с. : ил. – ISBN 5-88528-053-5."
    val actual = example.oneLevelDescriptionUnderMainTitle(rec_461)
    //println(actual)
    Assert.assertEquals(expected, actual)
  }

  /**
   * Одноуровневое под общим заглавием
   * с ответственностью в корне и в child
   */
  @Test
  def test2 = {
    val example = rusmarcRecord(Array(
      ("200", '0', ' ', Array(
        ('a', "Сб. 71"),
        ('f', "сост. А.П. Толстяков")
      )),
      ("210", ' ', ' ', Array(
        ('a', "М."),
        ('c', "Терра"),
        ('d', "1995")
      )),
      ("215", ' ', ' ', Array(
        ('a', "339 с."))),
      ("461", ' ', '0', Array(
        ('1', "2001#$aКнига. Исследования и материалы$vСб. 71")
      ))
    ))
    val rec_461 = rusmarcRecord(Array(
      ("200", '1', ' ', Array(
        ('a', "Книга. Исследования и материалы"),
        ('b', "Текст"),
        ('f', "РКП")
      )),
      ("210", ' ', ' ', Array(
        ('a', "М."),
        ('c', "Терра"),
        ('d', "1959")
      ))
    ))
    val expected = "Книга. Исследования и материалы [Текст]. Сб. 71. / РКП ; сост. А.П. Толстяков. – М. : Терра, 1995. – 339 с."
    val actual = example.oneLevelDescriptionUnderMainTitle(rec_461)
    //println(actual)
    Assert.assertEquals(expected, actual)
  }

  /**
   * Одноуровневое под общим заглавием
   * с ответственностью в корне, но без ответственности в child
   */
  @Test
  def test3 = {
    val example = rusmarcRecord(Array(
      ("200", '0', ' ', Array(
        ('a', "Сб. 71")
      )),
      ("210", ' ', ' ', Array(
        ('a', "М."),
        ('c', "Терра"),
        ('d', "1995")
      )),
      ("215", ' ', ' ', Array(
        ('a', "339 с."))),
      ("461", ' ', '0', Array(
        ('1', "2001#$aКнига. Исследования и материалы$vСб. 71")
      ))
    ))
    val rec_461 = rusmarcRecord(Array(
      ("200", '1', ' ', Array(
        ('a', "Книга. Исследования и материалы"),
        ('b', "Текст"),
        ('f', "РКП")
      )),
      ("210", ' ', ' ', Array(
        ('a', "М."),
        ('c', "Терра"),
        ('d', "1959")
      ))
    ))
    val expected = "Книга. Исследования и материалы [Текст]. Сб. 71. / РКП. – М. : Терра, 1995. – 339 с."
    val actual = example.oneLevelDescriptionUnderMainTitle(rec_461)
    //println(actual)
    Assert.assertEquals(expected, actual)
  }

  /**
   * Одноуровневое под общим заглавием
   * с ответственностью только в child
   */
  @Test
  def test4 = {
    val example = rusmarcRecord(Array(
      ("200", '0', ' ', Array(
        ('a', "Сб. 71"),
        ('f', "сост. А.П. Толстяков")
      )),
      ("210", ' ', ' ', Array(
        ('a', "М."),
        ('c', "Терра"),
        ('d', "1995")
      )),
      ("215", ' ', ' ', Array(
        ('a', "339 с."))),
      ("461", ' ', '0', Array(
        ('1', "2001#$aКнига. Исследования и материалы$vСб. 71")
      ))
    ))
    val rec_461 = rusmarcRecord(Array(
      ("200", '1', ' ', Array(
        ('a', "Книга. Исследования и материалы"),
        ('b', "Текст")
      )),
      ("210", ' ', ' ', Array(
        ('a', "М."),
        ('c', "Терра"),
        ('d', "1959")
      ))
    ))
    val expected = "Книга. Исследования и материалы [Текст]. Сб. 71. / сост. А.П. Толстяков. – М. : Терра, 1995. – 339 с."
    val actual = example.oneLevelDescriptionUnderMainTitle(rec_461)
    //println(actual)
    Assert.assertEquals(expected, actual)
  }

  /**
   * Одноуровневое под общим заглавием
   * без ответственности везде
   */
  @Test
  def test5 = {
    val example = rusmarcRecord(Array(
      ("200", '0', ' ', Array(
        ('a', "Сб. 71")
      )),
      ("210", ' ', ' ', Array(
        ('a', "М."),
        ('c', "Терра"),
        ('d', "1995")
      )),
      ("215", ' ', ' ', Array(
        ('a', "339 с."))),
      ("461", ' ', '0', Array(
        ('1', "2001#$aКнига. Исследования и материалы$vСб. 71")
      ))
    ))
    val rec_461 = rusmarcRecord(Array(
      ("200", '1', ' ', Array(
        ('a', "Книга. Исследования и материалы"),
        ('b', "Текст")
      )),
      ("210", ' ', ' ', Array(
        ('a', "М."),
        ('c', "Терра"),
        ('d', "1959")
      ))
    ))
    val expected = "Книга. Исследования и материалы [Текст]. Сб. 71. – М. : Терра, 1995. – 339 с."
    val actual = example.oneLevelDescriptionUnderMainTitle(rec_461)
    //println(actual)
    Assert.assertEquals(expected, actual)
  }

  /**
   * Одноуровневое под общим заглавием
   * с несколькими 200$e полями в корне и без указания материала
   */
  @Test
  def test6 = {
    val example = rusmarcRecord(Array(
      ("200", '0', ' ', Array(
        ('a', "Сб. 71")
      )),
      ("210", ' ', ' ', Array(
        ('a', "М."),
        ('c', "Терра"),
        ('d', "1995")
      )),
      ("215", ' ', ' ', Array(
        ('a', "339 с."))),
      ("461", ' ', '0', Array(
        ('1', "2001#$aКнига. Исследования и материалы$vСб. 71")
      ))
    ))
    val rec_461 = rusmarcRecord(Array(
      ("200", '1', ' ', Array(
        ('a', "Книга. Исследования и материалы"),
        ('e', "сб. ст."),
        ('e', "в 7 т."),
        ('f', "РКП")
      )),
      ("210", ' ', ' ', Array(
        ('a', "М."),
        ('c', "Терра"),
        ('d', "1959")
      ))
    ))
    val expected = "Книга. Исследования и материалы. Сб. ст. В 7 т. Сб. 71. / РКП. – М. : Терра, 1995. – 339 с."
    val actual = example.oneLevelDescriptionUnderMainTitle(rec_461)
    //println(actual)
    Assert.assertEquals(expected, actual)
  }

  /**
   * Одноуровневое под заглавием тома
   */
  @Test
  def test7 = {
    val example = rusmarcRecord(Array(
      ("010", ' ', ' ', Array(
        ('a', "5-8306-0368-5")
      )),
      ("200", '1', ' ', Array(
        ('a', "Белая обезьяна; Идиллия; Серебряная ложка"),
        ('e', "Ч.1,2"),
        ('f', "Д. Голсуорси")
      )),
      ("210", ' ', ' ', Array(
        ('a', "Тула"),
        ('c', "Левша"),
        ('d', "1993")
      )),
      ("215", ' ', ' ', Array(
        ('a', "608 с."))),
      ("461", ' ', '0', Array(
        ('1', "2001#$aСага о Форсайтах$v4")
      ))
    ))
    val rec_461 = rusmarcRecord(Array(
      ("200", '1', ' ', Array(
        ('a', "Сага о Форсайтах"),
        ('f', "Д. Голсуорси")
      )),
      ("210", ' ', ' ', Array(
        ('a', "Тула"),
        ('c', "Левша"),
        ('d', "1993")
      ))
    ))
    val expected = "Белая обезьяна; Идиллия; Серебряная ложка : Ч.1,2 / Д. Голсуорси. – Тула : Левша, 1993. – 608 с. – (Сага о Форсайтах / Д. Голсуорси ; 4). – ISBN 5-8306-0368-5."
    val actual = example.oneLevelDescriptionUnderVolumeTitle(rec_461)
    println(actual)
    Assert.assertEquals(expected, actual)
  }

}