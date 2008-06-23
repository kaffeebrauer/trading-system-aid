package au.com.openbiz.commons.helper.string;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.springframework.util.StringUtils;

public class StringHelper {

	@SuppressWarnings("unchecked")
	public static <T> String createCommaDelimitedStringOfIds(Collection<T> elements) {
		Transformer invokerTransformer = InvokerTransformer.getInstance("getId");
		List<Integer> elementId = (List<Integer>)CollectionUtils.collect(elements, invokerTransformer);
		return StringUtils.collectionToCommaDelimitedString(elementId);
	}
}
