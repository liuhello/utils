package skip_list;

import java.lang.reflect.Array;
import java.util.Random;

public class SkipList<E,C extends Comparator<E>> {

	protected int m_maxLevel;
	protected int m_level;
	protected SkipNode m_header;
	protected C m_compator;
	protected Random m_random = new Random(System.currentTimeMillis());
	
	public SkipList(int maxLevel,C c) {
		m_maxLevel = maxLevel;
		m_level = 0;
		m_header = newNode(maxLevel);
		m_compator = c;
	}
	
	public boolean check() {
		SkipNode node = m_header.m_forward[0];
		while(node != null && node.m_forward[0] != null) {
			if(m_compator.compare(node.m_elem, node.m_forward[0].m_elem) >= 0) {
				return false;
			}
			node = node.m_forward[0];
		}
		return true;
	}
	
	public void add(E e) {
		SkipNode[] update = newArray(m_maxLevel);
		for(int i = 0;i < m_maxLevel;i++) {
			update[i] = m_header;
		}
		int k = m_level;
		SkipNode q,p = m_header;
		do{
			q = p.m_forward[k];
			while(q != null&&m_compator.compare(q.m_elem, e) <= 0) {
				p = q;
				q = q.m_forward[k];
			}
			update[k] = p;
			k--;			
		} while(k >= 0);
		if(m_compator.compare(update[0].m_elem, e) == 0) {
			update[0].m_elem = e;
			return;
		}
		int level = randomLevel();
		if(level > m_level) {
			level = m_level + 1;
			m_level = level;
		}
		SkipNode node = newNode(level+1);
		node.m_elem = e;
		for(int i = 0;i <= level;i++) {
			node.m_forward[i] = update[i].m_forward[i];
			update[i].m_forward[i] = node;
		}
	}
	
	public void delete(E e) {
		SkipNode[] update = newArray(m_maxLevel);
		for(int i = 0;i < m_maxLevel;i++) {
			update[i] = m_header;
		}
		int k = m_level;
		SkipNode q,p = m_header;
		do{
			q = p.m_forward[k];
			while(q != null&&m_compator.compare(q.m_elem, e) < 0) {
				p = q;
				q = q.m_forward[k];
			}
			update[k] = p;
			k--;			
		} while(k >= 0);
		if(update[0].m_forward[0] == null || m_compator.compare(update[0].m_forward[0].m_elem, e) != 0) {
			return;
		}
		for(int i = m_level;i >= 0;i--) {
			if(update[i].m_forward[i] != null && m_compator.compare(update[i].m_forward[i].m_elem, e) == 0) {
				update[i].m_forward[i] = update[i].m_forward[i].m_forward[i];
			}
		}
		if(m_header.m_forward[m_level] == null) {
			m_level -= 1;
		}
	}
	
	public E get(E e) {
		int k = m_level;
		SkipNode q,p = m_header;
		do{
			q = p.m_forward[k];
			while(q != null&&m_compator.compare(q.m_elem, e) < 0) {
				p = q;
				q = q.m_forward[k];
			}
			if(q != null&&m_compator.compare(q.m_elem, e) == 0) {
				return q.m_elem;
			}
			k--;			
		} while(k >= 0);
		return null;
	}
	
	public E get(int index) {
		SkipNode node = m_header.m_forward[0];
		if(index == 0) {
			return node.m_elem;
		}
		while(node != null) {
			node = node.m_forward[0];
			index --;
			if(index == 0) {
				return node.m_elem;
			}
		}
		return null;
	}
	
	
	public boolean contains(E e) {
		return get(e) != null;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("[");
		SkipNode node = m_header.m_forward[0];
		while(node != null) {
			sb.append(node.m_elem);
			if(node.m_forward[0] != null) {
				sb.append(",");
			}
			node = node.m_forward[0];
		}
		sb.append("]");
		return sb.toString();
	}
	
	private int randomLevel() {
		return m_random.nextInt(m_maxLevel);
	}
	
	private SkipNode newNode(int level) {
		SkipNode node = new SkipNode();
		node.m_forward = newArray(level);
		return node;
	}
	
	@SuppressWarnings("unchecked")
	private SkipNode[] newArray(int size) {
		//SkipNode<E> node = new SkipNode<E>();
		return (SkipNode[])Array.newInstance(SkipNode.class, size);
	}
	
	
	class SkipNode {
		E m_elem;
		SkipNode[] m_forward;
	}
	
}
