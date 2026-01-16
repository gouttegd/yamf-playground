import unittest

from incenp.yamf.playground.nested import read_bar, read_base, read_foo, read_foobar, get_bar, get_foo

class TestDirect(unittest.TestCase):
    """Test reading data from files that use the "nested" method of embedding extra metadata."""

    def test_base_impl(self):
        """Test from an implementation supporting only the base model."""

        ins = read_base("../samples/test-extended-nested.json")
        self.assertEqual("The Model Name", ins.model.modelName)

        # This implementation does not support them, but data from the
        # foo and bar extensions are there
        self.assertTrue(hasattr(ins, "com.example.foo"))
        self.assertTrue(hasattr(ins, "org.example.bar"))

    def test_foo_impl(self):
        """Test from an implementation supporting base+foo."""

        ins = read_foo("../samples/test-extended-nested.json")
        self.assertEqual("The Model Name", ins.model.modelName)
        self.assertEqual("The name of the Foo", ins.foo.fooName)
        
        # This implementation does not support it, but bar data is there
        self.assertTrue(hasattr(ins, "org.example.bar"))

    def test_bar_impl(self):
        """Test from an implementation supporting base+bar."""

        ins = read_bar("../samples/test-extended-nested.json")
        self.assertEqual("The Model Name", ins.model.modelName)
        self.assertEqual("The name of the Bar", ins.bar.barName)

        # This implementation does not support it, but foo data is there
        self.assertTrue(hasattr(ins, "com.example.foo"))

    def test_foobar_impl(self):
        """Test from an implementation supporting base+foo+bar."""

        ins = read_foobar("../samples/test-extended-nested.json")
        self.assertEqual("The Model Name", ins.model.modelName)
        self.assertEqual("The name of the Foo", ins.foo.fooName)
        self.assertEqual("The name of the Bar", ins.bar.barName)

    def test_composed(self):
        """Test composition model."""

        ins = read_base("../samples/test-extended-nested.json")
        self.assertEqual("The Model Name", ins.model.modelName)

        # Explicit support of Foo
        fooExtension = get_foo(ins)
        self.assertEqual("The name of the Foo", fooExtension.fooName)

        # Explicit support of Bar
        barExtension = get_bar(ins)
        self.assertEqual("The name of the Bar", barExtension.barName)
