import unittest

from incenp.yamf.playground.nested import read_base, read_foo, get_foo

class TestDirect(unittest.TestCase):
    """Test reading data from files that use the "nested" method of embedding extra metadata."""

    def test_base_impl(self):
        """Test from an implementation supporting only the base model."""

        ins = read_base("../samples/pidinst/pidinst-extended-nested.json")
        self.assertEqual("The Model Name", ins.model.name)

        # This implementation does not support them, but data from the
        # foo and bar extensions are there
        self.assertTrue(hasattr(ins, "com.example.foo"))
        self.assertTrue(hasattr(ins, "org.example.bar"))

    def test_foo_impl(self):
        """Test from an implementation supporting base+foo."""

        ins = read_foo("../samples/pidinst/pidinst-extended-nested.json")
        self.assertEqual("The Model Name", ins.model.name)
        self.assertEqual("The name of the Foo", ins.foo.name)
        
        # This implementation does not support it, but bar data is there
        self.assertTrue(hasattr(ins, "org.example.bar"))

    def test_composed(self):
        """Test composition model."""

        ins = read_base("../samples/pidinst/pidinst-extended-nested.json")
        self.assertEqual("The Model Name", ins.model.name)

        # Explicit support of Foo
        fooExtension = get_foo(ins)
        self.assertEqual("The name of the Foo", fooExtension.name)
